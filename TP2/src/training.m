function w = training()
  config;
  
  #Dataset inputs
  e = dataset(1:patterns, 1:end - outputs);
  
  #Dataset outputs
  s = dataset(1:patterns, end - outputs + 1:end);
  
  # Random weight initialization
  w = {rand(hidden_layers(1), columns(e) + 1) - 0.5};
  for i = 1:length(hidden_layers) - 1
    w{end + 1} = rand(hidden_layers(i + 1), hidden_layers(i) + 1) - 0.5;
  endfor
  w{end + 1} = rand(outputs, hidden_layers(end) + 1) - 0.5;
  
  # Iterate until all patterns match calculated output with expected output
  redo = true;
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
      
      if abs(v{end} - s(index)) > epsilon
        if abs(v{end} - s(index)) > max
          max = abs(v{end} - s(index));
        endif
        redo = true;
        
        d{length(v)} = arrayfun(derivatives{end}, w{end}(:, 2:end) * v{end - 1}(2:end, :)) .* (s(index) - v{end});
        for i = 1:length(d) - 2
          d{end - i} = arrayfun(derivatives{end - i}, w{end - i}(:, 2:end) * v{end - i - 1}(2:end, :)) .* (w{end - i + 1}(:, 2:end)' * d{end - i + 1});
        endfor
        
        for i = 1:length(w)
          dw = eta * (d{i + 1} * v{i}');
          w{i} = w{i} + dw;
        endfor
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
