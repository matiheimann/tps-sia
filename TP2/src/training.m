function [w, normalization_mins, normalization_maxs] = training()
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
  
  # Random weight initialization
  w = {rand(hidden_layers(1), columns(e) + 1) - 0.5};
  for i = 1:length(hidden_layers) - 1
    w{end + 1} = rand(hidden_layers(i + 1), hidden_layers(i) + 1) - 0.5;
  endfor
  w{end + 1} = rand(outputs, hidden_layers(end) + 1) - 0.5;
  
  # Iterate until all patterns match calculated output with expected output
  redo = true;
  for i = 1:length(w)
    last_dw{i} = zeros(rows(w{i}), columns(w{i}));
  endfor
  while (redo)
    order = randperm(rows(e));
    redo = false;
    max_error = 0;
    while(!isempty(order))
      index = order(end);
      order(end) = [];
      
      v = {[-1, e(index, :)]'};
      
      for i = 1:length(hidden_layers)
        v{i + 1} = arrayfun(functions{fn_index}, w{i} * v{i}, beta);
        v{i + 1} = [-1; v{i + 1}];
      endfor
      v{end + 1} = arrayfun(functions{fn_index}, w{end} * v{end}, beta);

      #error = sum(abs(s(index, :)' - v{end}));
      #if error > epsilon
      error = sum((s(index, :)' - v{end}) .^ 2) / outputs;
      if error > epsilon ^ 2
        if error > max_error
          max_error = error;
        endif
        redo = true;
        
        d{length(v)} = arrayfun(derivatives{fn_index}, w{end} * v{end - 1}, beta) .* (s(index) - v{end});
        for i = 1:length(d) - 2
          d{end - i} = arrayfun(derivatives{fn_index}, w{end - i} * v{end - i - 1}, beta) .* (w{end - i + 1}(:, 2:end)' * d{end - i + 1});
        endfor
        
        for i = 1:length(w)
          dw{i} = eta * (d{i + 1} * v{i}') + momentum_alpha * last_dw{i};
          w{i} = w{i} + dw{i};
        endfor
        last_dw = dw;
      endif
      
    endwhile
    max_error
  endwhile
endfunction
