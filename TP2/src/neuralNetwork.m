function z = neuralNetwork(e, w)
  
  v = {[-1; e]};

  for i = 1:length(hidden_layers)
    v{i + 1} = arrayfun(functions{i}, w{i} * v{i});
    v{i + 1} = [-1; v{i + 1}];
  endfor
  v{end + 1} = arrayfun(functions{end}, w{end} * v{end});
  
  z = v{end};
  
endfunction
