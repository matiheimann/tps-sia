function w = training()
  
  #Dataset inputs
  e = dataset(1:patterns, 1:end - outputs);
  
  #Dataset outputs
  s = dataset(1:patterns, end - outputs + 1:end);
  
  # Inputs for next layer (number of columns = number of patterns)
  v = {[-1 * ones(rows(e), 1), e]'};
  
  # Random weight initialization
  w = {rand(hidden_layers(1), columns(e) + 1) - 0.5};
  for i = 1:length(hidden_layers) - 1
    w{i + 1} = rand(hidden_layers(i + 1), hidden_layers(i) + 1) - 0.5;
  endfor
  w{end + 1} = rand(outputs, hidden_layers(end) + 1) - 0.5;
  
  # Iterate until all patterns match calculated output with expected output
  redo = true;
  while(redo)
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
    
    if(abs(V{end} - M(index, end)) < epsilon)
      redo = false;
    endif
  endwhile
  
endfunction
