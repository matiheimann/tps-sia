function z = neuralNetwork(e, w)
  config;
  
  v = {[-1, e]'};
  v{length(hidden_layers) + 2} = zeros(outputs, 1);

  for i = 1:length(hidden_layers)
    v{i + 1} = arrayfun(functions{i}, w{i} * v{i});
    v{i + 1} = [-1; v{i + 1}];
  endfor
  v{end + 1} = arrayfun(functions{end}, w{end} * v{end});
  
  z = v{end}';
  
endfunction
