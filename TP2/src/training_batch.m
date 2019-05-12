function w = training_batch()
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
  
  # Neurons data initialization
  v = {[-1 * ones(1, rows(e)); e']};
  for i = 1:length(hidden_layers)
    v{i + 1} = [-1 * ones(1, rows(e)); zeros(hidden_layers(i), rows(e))];
    d{i + 1} = zeros(hidden_layers(i), rows(e));
  endfor
  v{end + 1} = zeros(outputs, rows(e));
  d{end + 1} = zeros(outputs, rows(e));
  
  # Random weight initialization
  global w = {rand(hidden_layers(1), columns(e) + 1) * ((1/sqrt(columns(e) + 1)) - (-1/sqrt(columns(e) + 1))) + (-1/sqrt(columns(e) + 1))};
  for i = 1:length(hidden_layers) - 1
    w{i + 1} = rand(hidden_layers(i + 1), hidden_layers(i) + 1) * ((1/sqrt(hidden_layers(i) + 1)) - (-1/sqrt(hidden_layers(i) + 1))) + (-1/sqrt(hidden_layers(i) + 1));
  endfor
  w{end + 1} = rand(outputs, hidden_layers(end) + 1) * ((1/sqrt(hidden_layers(end) + 1)) - (-1/sqrt(hidden_layers(end) + 1))) + (-1/sqrt(hidden_layers(end) + 1));
  
  # Variables initialization
  for i = 1:length(w)
    w_0{i} = zeros(rows(w{i}), columns(w{i}));
  endfor
  last_dw = w_0;
  adam_m = w_0;
  adam_v = w_0;
  epochs = 1;
  epoch_etas = [];
  epoch_errors = [];
  epoch_reduction_steps = 0;
  epoch_last_w = w;
  redo = true;
  start_time = time();
  
  # Iterate until desired epsilon (mean) or max epochs
  while (redo && (max_epochs == -1 || epochs < max_epochs))
    
    redo = false;
    
    # Calculate neurons values for each pattern with actual weights  
    for i = 1:length(hidden_layers)
      v{i + 1}(2:end, :) = arrayfun(functions{fn_index}, w{i} * v{i}, beta);
    endfor
    v{end} = arrayfun(functions{fn_index}, w{end} * v{end - 1}, beta);
    
    # Calculate output error (ecm) for each pattern and get mean
    error = mean(sum((s' - v{end}) .^ 2, 1) / outputs);
    if error > epsilon ^ 2
      redo = true;
    endif
    epoch_errors(end + 1) = error;
    epoch_etas(end + 1) = eta;
    
    # Print last error and graph etas and error for each epoch
    error
    if epochs == 1
      figure(1);
      error_plot = plot(epoch_errors, 1:length(epoch_errors));
      figure(2);
      etas_plot = plot(epoch_etas, 1:length(epoch_etas));
    else
      set(error_plot, 'XData', 1:length(epoch_errors));
      set(error_plot, 'YData', epoch_errors);
      set(etas_plot, 'XData', 1:length(epoch_etas));
      set(etas_plot, 'YData', epoch_etas);
      refresh();
    endif
    
    # Adaptative eta algorithm 2
    ##{ 
    if (adaptative_eta == true && length(epoch_errors) > 1)
      if (epoch_errors(end) - epoch_errors(end - 1) < 0)
        # Save last good weights
        epoch_last_w = w;
        eta_max = eta;
        
        epoch_reduction_steps++;
        if (mod(epoch_reduction_steps, epoch_min_reduction_steps) == 0)
          eta = eta + eta_a;
        endif
      else
        if epochs > 100
          if epoch_reduction_steps > epoch_min_reduction_steps
            eta_max = eta - eta_b * eta;
            w = epoch_last_w;
          endif
          eta = eta_max;
          if eta < eta_min
            eta = eta_min;
          endif
        endif
        epoch_reduction_steps = 0;
        epochs--;
        epoch_etas = epoch_etas(1:end-1);
        epoch_errors = epoch_errors(1:end-1);
      endif
    endif
    #}
    
    # Adaptative eta algorithm 1
    #{ 
    if (eta_a != 0 && eta_b != 0 && length(epoch_errors) > 1)
      if (epoch_errors(end) - epoch_errors(end - 1) < 0)
        # Save last good weights
        epoch_last_w = w;
        
        epoch_reduction_steps++;
        if (mod(epoch_reduction_steps, epoch_min_reduction_steps) == 0)
          #{
          if eta_max - eta > 0 
            eta = eta + eta_a * sqrt((eta_max - eta) / eta);
          endif
          #}
          eta = eta + eta_a;
        endif
      else
        #{
        if (epochs > 100 && epoch_reduction_steps >= 10)
          eta_max = eta - eta_b * eta;
        endif
        #}
        epoch_reduction_steps = 0;
        if ((eta - eta_b * eta) >= eta_min) 
          eta = eta - eta_b * eta;
        else
          eta = eta_min;
        endif
        epochs--;
        epoch_etas = epoch_etas(1:end-1);
        epoch_errors = epoch_errors(1:end-1);
        w = epoch_last_w;
      endif
    endif
    #}
    
    if redo
      
      # Calculate new weights
      d{end} = arrayfun(derivatives{fn_index}, w{end} * v{end - 1}, beta) .* (s' - v{end});
      for i = 1:length(d) - 2
        d{end - i} = arrayfun(derivatives{fn_index}, w{end - i} * v{end - i - 1}, beta) .* (w{end - i + 1}(:, 2:end)' * d{end - i + 1});
      endfor
          
      for i = 1:length(w)
        g{i} = (d{i + 1} * v{i}');
        
        switch (weight_optimization)
          # SGD
          case 0
            dw{i} = eta * g{i};
          # Momentum
          case 1
            dw{i} = eta * g{i} + momentum_alpha * last_dw{i};
          # Adam
          case 2
            adam_m{i} = adam_beta1 * adam_m{i} + (1 - adam_beta1) * g{i};
            adam_v{i} = adam_beta2 * adam_v{i} + (1 - adam_beta2) * (g{i} .^ 2);
            m_hat = adam_m{i} / (1 - (adam_beta1 ^ epochs));
            v_hat = adam_v{i} / (1 - (adam_beta2 ^ epochs));
            dw{i} = eta * m_hat ./ (sqrt(v_hat) + adam_epsilon);
          # Adamax  
          case 3
            adam_m{i} = adam_beta1 * adam_m{i} + (1 - adam_beta1) * g{i};
            m_hat = adam_m{i} / (1 - (adam_beta1 ^ epochs));
            adam_v{i} = max(adam_beta2 * adam_v{i}, abs(g{i}));
            dw{i} = eta * m_hat ./ adam_v{i};    
          # Nadam
          case 4
            adam_m{i} = adam_beta1 * adam_m{i} + (1 - adam_beta1) * g{i};
            adam_v{i} = adam_beta2 * adam_v{i} + (1 - adam_beta2) * (g{i} .^ 2);
            m_hat = adam_m{i} / (1 - (adam_beta1 ^ epochs)) + (1 - adam_beta1) * g{i} / (1 - (adam_beta1 ^ epochs));
            v_hat = adam_v{i} / (1 - (adam_beta2 ^ epochs));
            dw{i} = eta * m_hat ./ (sqrt(v_hat) + adam_epsilon);
        endswitch

        w{i} = w{i} + dw{i};
      endfor
      last_dw = dw;
    endif
    
    epochs++;
  endwhile
  printf("Epochs: %d\n", epochs);
  printf("Training time: %d seconds.\n", time() - start_time);
endfunction
