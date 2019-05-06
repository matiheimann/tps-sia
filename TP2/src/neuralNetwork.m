function z = neuralNetwork(e, w, normalization_mins, normalization_maxs)
  config;
  
  for i = 1:columns(e)
    e(i) = normalize(e(i), normalization_ranges(i, :), normalization_mins{i}, normalization_maxs{i});
  endfor
  
  v = {[-1, e]'};
      
  for i = 1:length(hidden_layers)
    v{i + 1} = arrayfun(functions{fn_index}, w{i} * v{i}, beta);
    v{i + 1} = [-1; v{i + 1}];
  endfor
  v{end + 1} = arrayfun(functions{fn_index}, w{end} * v{end}, beta);

  z = v{end}';
  for i = 1:columns(z)
    z(i) = denormalize(z(i), normalization_ranges(columns(e) + i, :), normalization_mins{columns(e) + i}, normalization_maxs{columns(e) + i});
  endfor

endfunction
