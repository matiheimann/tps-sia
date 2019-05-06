function [w, min, max] = training()
  config;
  
  # Dataset inputs and outputs
  if(!isempty(input_patterns))
    # Validar no repetidos y porcentaje menor a input list...
    pattern_qty = round(patterns * rows(dataset)) - length(input_patterns);
    n = setdiff(1:rows(dataset), input_patterns);
    arr_aux = randperm(length(n));
    training_set = [n(arr_aux)(1:pattern_qty) input_patterns];
    arr_aux = randperm(length(training_set));
    training_set = training_set(arr_aux);
  else
    pattern_qty = round(patterns * rows(dataset));
    training_set = randperm(rows(dataset))(1:pattern_qty);
  endif
  
  for i = 1:pattern_qty
    e(i, :) = dataset(training_set(i), 1:end - outputs);
    s(i, :) = dataset(training_set(i), end - outputs + 1:end);
  endfor
  
  # Normalization
  for i = 1:columns(e)
    e(:, i) = normalize(e(:, i), -1, 1);
  endfor
  
  for i = 1:columns(s)
    s(:, i) = normalize(s(:, i), -1, 1);
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
    max = 0;
    while(!isempty(order))
      index = order(end);
      order(end) = [];
      
      v = {[-1, e(index, :)]'};
      
      for i = 1:length(hidden_layers)
        v{i + 1} = arrayfun(functions{i}, w{i} * v{i});
        v{i + 1} = [-1; v{i + 1}];
      endfor
      v{end + 1} = arrayfun(functions{end}, w{end} * v{end});

      # error = 0.5 * sum((s(index, :)' - v{end}) .^ 2) / outputs;
      error = sum(abs(s(index, :)' - v{end}));
      if error > epsilon
        if error > max
          max = error;
        endif
        redo = true;
        
        d{length(v)} = arrayfun(derivatives{end}, w{end}(:, 2:end) * v{end - 1}(2:end, :)) .* (s(index) - v{end});
        for i = 1:length(d) - 2
          d{end - i} = arrayfun(derivatives{end - i}, w{end - i}(:, 2:end) * v{end - i - 1}(2:end, :)) .* (w{end - i + 1}(:, 2:end)' * d{end - i + 1});
        endfor
        
        for i = 1:length(w)
          dw{i} = eta * (d{i + 1} * v{i}') + momentum_alpha * last_dw{i};
          w{i} = w{i} + dw{i};
        endfor
        last_dw = dw;
      endif
      
    endwhile
    max
  endwhile
    
    # ...
    # O = randperm(rows(M));
    # redo = false;
    # num = num + 1;
    
    # while(!isempty(O))
    #  index = O(end);
    #  O(end) = [];
    #  V = {M(index,1:end-1)'};
    #  H = {W{1} * V{end}};
    #  V{end+1} = [-1; g(H{end})];
    #  H{end+1} = W{2} * V{end};
    #  V{end+1} = g(H{end});
    #  delta{3} = g_der(H{end}).*(M(index,end)-V{end});
      
    #  delta{2} = g_der(H{1}(1)) * dot(W{2}(:,2), delta{3});
    #  delta{2} = [delta{2}; g_der(H{1}(2)) * dot(W{2}(:,3), delta{3})];
      
    #  deltaW = n * delta{2} .* V{1}';
    #  W{1} = W{1} + deltaW;
      
    #  deltaW = n * delta{3} .* V{2}';
    #  W{2} = W{2} + deltaW;
      
    #  if(abs(V{end} - M(index,end)) > eps)
    #    redo = true;
    #  endif
    # endwhile
  
endfunction
