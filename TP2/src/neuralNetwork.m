function z = neuralNetwork(e, w)
  config;
  
  v = {[-1, e]'};
      
  for i = 1:length(hidden_layers)
    v{i + 1} = arrayfun(functions{fn_index}, w{i} * v{i}, beta);
    v{i + 1} = [-1; v{i + 1}];
  endfor
  v{end + 1} = arrayfun(functions{fn_index}, w{end} * v{end}, beta);

  z = v{end}';

endfunction
