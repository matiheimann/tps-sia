function [w, normalization_mins, normalization_maxs] = training_batch()
  config;
  validate_config();
  
  # Dataset inputs and outputs
  [e, s] = randomize_training_patterns(dataset, patterns, input_patterns, outputs);
  
  # Normalization
  for i = 1:columns(e)
    normalization_mins{i} = min(e(:, i));
    normalization_maxs{i} = max(e(:, i));
    e(:, i) = normalize(e(:, i), normalization_ranges(i, :), normalization_mins{i}, normalization_maxs{i});
  endfor
  
  for i = 1:columns(s)
    normalization_mins{columns(e) + i} = min(s(:, i));
    normalization_maxs{columns(e) + i} = max(s(:, i));
    s(:, i) = normalize(s(:, i), normalization_ranges(columns(e) + i, :), normalization_mins{columns(e) + i}, normalization_maxs{columns(e) + i});
  endfor
  
  v = {[-1 * ones(1, rows(e)); e']};
  for i = 1:length(hidden_layers)
    v{i + 1} = [-1 * ones(1, rows(e)); zeros(hidden_layers(i), rows(e))];
    d{i + 1} = zeros(hidden_layers(i), rows(e));
  endfor
  v{end + 1} = zeros(outputs, rows(e));
  d{end + 1} = zeros(outputs, rows(e));
  
  # Random weight initialization
  w = {rand(hidden_layers(1), columns(e) + 1) - 0.5};
  for i = 1:length(hidden_layers) - 1
    w{i + 1} = rand(hidden_layers(i + 1), hidden_layers(i) + 1) - 0.5;
  endfor
  w{end + 1} = rand(outputs, hidden_layers(end) + 1) - 0.5;
  
  # Iterate until all patterns match calculated output with expected output
  for i = 1:length(w)
    last_dw_0{i} = zeros(rows(w{i}), columns(w{i}));
  endfor
  last_dw = last_dw_0;
  epochs = 0;
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
    
    #{  
    if (eta_a != 0 && eta_b != 0 && length(epoch_errors) > 1)
      if (epoch_errors(end) < epoch_errors(end - 1))
        epoch_reduction_steps++;
        if (epoch_reduction_steps == epoch_min_reduction_steps)
          epoch_reduction_steps = 0;
          eta = eta + eta_a
        endif
      else
        if epoch_reduction_steps > 0
          epoch_reduction_steps--;
        endif
        if ((eta - eta_b * eta) >= eta_min) 
          epochs--;
          epoch_errors = epoch_errors(:, 1:end-1);
          eta = eta - eta_b * eta
          w = epoch_last_w;
          last_dw = last_dw_0;
        endif
      endif
    endif
    #}
    
    if redo
      epoch_last_w = w;
      
      d{end} = arrayfun(derivatives{fn_index}, w{end} * v{end - 1}, beta) .* (s' - v{end});
      for i = 1:length(d) - 2
        d{end - i} = arrayfun(derivatives{fn_index}, w{end - i} * v{end - i - 1}, beta) .* (w{end - i + 1}(:, 2:end)' * d{end - i + 1});
      endfor
          
      for i = 1:length(w)
        dw{i} = eta * (d{i + 1} * v{i}') + momentum_alpha * last_dw{i};
        w{i} = w{i} + dw{i};
      endfor
      last_dw = dw;
    endif
    
    epochs++;
    error
  endwhile
  printf("Epochs: %d\n", epochs);
  printf("Training time: %d seconds.\n", time() - start_time);
endfunction
