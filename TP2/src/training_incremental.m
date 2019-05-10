function w = training_incremental()
  config;
  validate_config();
  
  # Dataset inputs and outputs
  [e, s] = randomize_training_patterns(dataset, patterns, input_patterns, outputs);
  
  # Normalization
  for i = 1:columns(e)
    e(:, i) = normalize(e(:, i), normalization_ranges(i, :), normalization_mins(i), normalization_maxs(i));
  endfor
  
  for i = 1:columns(s)
    s(:, i) = normalize(s(:, i), normalization_ranges(columns(e) + i, :), normalization_mins(columns(e) + i), normalization_maxs(columns(e) + i));
  endfor
  
  v = {[-1 * ones(1, rows(e)); e']};
  for i = 1:length(hidden_layers)
    v{i + 1} = [-1 * ones(1, rows(e)); zeros(hidden_layers(i), rows(e))];
    d{i + 1} = zeros(hidden_layers(i), rows(e));
  endfor
  v{end + 1} = zeros(outputs, rows(e));
  d{end + 1} = zeros(outputs, rows(e));
  
  # Random weight initialization
  w = {rand(hidden_layers(1), columns(e) + 1) * ((1/sqrt(columns(e) + 1)) - (-1/sqrt(columns(e) + 1))) + (-1/sqrt(columns(e) + 1))};
  for i = 1:length(hidden_layers) - 1
    w{i + 1} = rand(hidden_layers(i + 1), hidden_layers(i) + 1) * ((1/sqrt(hidden_layers(i) + 1)) - (-1/sqrt(hidden_layers(i) + 1))) + (-1/sqrt(hidden_layers(i) + 1));
  endfor
  w{end + 1} = rand(outputs, hidden_layers(end) + 1) * ((1/sqrt(hidden_layers(end) + 1)) - (-1/sqrt(hidden_layers(end) + 1))) + (-1/sqrt(hidden_layers(end) + 1));
  
  # Iterate until all patterns match calculated output with expected output
  for i = 1:length(w)
    last_dw_0{i} = zeros(rows(w{i}), columns(w{i}));
  endfor
  last_dw = last_dw_0;
  epochs = 0;
  epoch_etas = [];
  epoch_errors = [];
  epoch_reduction_steps = 0;
  epoch_last_w = w;
  redo = true;
  start_time = time();
  while (redo && (max_epochs == -1 || epochs < max_epochs))
    
    redo = false;
    
    for i = 1:length(hidden_layers)
      v{i + 1}(2:end, :) = arrayfun(functions{fn_index}, w{i} * v{i}, beta);
    endfor
    v{end} = arrayfun(functions{fn_index}, w{end} * v{end - 1}, beta);

    error = mean(sum((s' - v{end}) .^ 2, 1) / outputs);
    if error > epsilon ^ 2
      redo = true;
    endif
    epoch_errors(end + 1) = error;
    epoch_etas(end + 1) = eta;
    
    error
    if epochs == 0
      figure(1);
      error_plot = plot(epoch_errors, 0:epochs);
      figure(2);
      etas_plot = plot(epoch_etas, 0:epochs);
    else
      set(error_plot, 'XData', 0:epochs);
      set(error_plot, 'YData', epoch_errors);
      set(etas_plot, 'XData', 0:epochs);
      set(etas_plot, 'YData', epoch_etas);
      refresh();
    endif
    
    ##{ 
    if (eta_a != 0 && eta_b != 0 && length(epoch_errors) > 1)
      if (epoch_errors(end) - epoch_errors(end - 1) < 0)
        epoch_reduction_steps++;
        if (epoch_reduction_steps == epoch_min_reduction_steps)
          epoch_reduction_steps = 0;
          eta = eta + eta_a;
        endif
      else
        if epoch_reduction_steps > 0
          epoch_reduction_steps = 0;
        endif
        if ((eta - eta_b * eta) >= eta_min) 
          epochs--;
          epoch_etas = epoch_etas(1:end-1);
          epoch_errors = epoch_errors(1:end-1);
          eta = eta - eta_b * eta;
          w = epoch_last_w;
          last_dw = last_dw_0;
        endif
      endif
    endif
    #}
    
    if redo
      epoch_last_w = w;
      
      order = randperm(rows(e));
      while(!isempty(order))
        index = order(end);
        order(end) = [];
        
        for i = 1:length(hidden_layers)
          v{i + 1}(2:end, index) = arrayfun(functions{fn_index}, w{i} * v{i}(:, index), beta);
        endfor
        v{end}(:, index) = arrayfun(functions{fn_index}, w{end} * v{end - 1}(:, index), beta);

        d{end}(:, index) = arrayfun(derivatives{fn_index}, w{end} * v{end - 1}(:, index), beta) .* (s(index) - v{end}(:, index));
        for i = 1:length(d) - 2
          d{end - i}(:, index) = arrayfun(derivatives{fn_index}, w{end - i} * v{end - i - 1}(:, index), beta) .* (w{end - i + 1}(:, 2:end)' * d{end - i + 1}(:, index));
        endfor
          
        for i = 1:length(w)
          dw{i} = eta * (d{i + 1}(:, index) * v{i}(:, index)') + momentum_alpha * last_dw{i};
          w{i} = w{i} + dw{i};
        endfor
        last_dw = dw;
      endwhile
    endif
    
    epochs++;
  endwhile
  printf("Epochs: %d\n", epochs);
  printf("Training time: %d seconds.\n", time() - start_time);
endfunction
