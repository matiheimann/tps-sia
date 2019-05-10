function diff = neural_network(w)
  config;
  
  e = dataset(:, 1:end - outputs);
  s = dataset(:, end - outputs + 1:end);
  
  for i = 1:columns(e)
    e(:, i) = normalize(e(:, i), normalization_ranges(i, :), normalization_mins(i), normalization_maxs(i));
  endfor
  
  v = {[-1 * ones(1, rows(e)); e']};
  for i = 1:length(hidden_layers)
    v{i + 1} = [-1 * ones(1, rows(e)); zeros(hidden_layers(i), rows(e))];
    d{i + 1} = zeros(hidden_layers(i), rows(e));
  endfor
  v{end + 1} = zeros(outputs, rows(e));
  
  for i = 1:length(hidden_layers)
    v{i + 1}(2:end, :) = arrayfun(functions{fn_index}, w{i} * v{i}, beta);
  endfor
  v{end} = arrayfun(functions{fn_index}, w{end} * v{end - 1}, beta);
  
  z = v{end}';
  
  for i = 1:columns(z)
    z(:, i) = denormalize(z(:, i), normalization_ranges(columns(e) + i, :), normalization_mins(columns(e) + i), normalization_maxs(columns(e) + i));
  endfor
  
  diff = abs(z - s);
  
  generalization_capability = sum(diff < epsilon);
  printf("Generalization Capability = %d%%\n", (generalization_capability / rows(s)) * 100);
  
  limits_x = linspace(min(e(:, 1)), max(e(:, 1)));
  limits_y = linspace(min(e(:, 2)), max(e(:, 2)));
  [x, y] = meshgrid(limits_x, limits_y);
  z_data = griddata(e(:, 1), e(:, 2), s, x, y);
  z_generated = griddata(e(:, 1), e(:, 2), z, x, y);
  z_error = griddata(e(:, 1), e(:, 2), diff, x, y);
  
  figure(1)
  surf(x, y, z_data)
  title("Data Terrain")
  
  figure(2)
  surf(x, y, z_generated)
  title("Generated Terrain");
  
  figure(3)
  surf(x, y, z_error)
  title("Error Terrain");

endfunction
