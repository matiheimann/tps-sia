function normalized = normalize(array, range, min, max)
  % Normalize to [0, 1]:
  array = (array - min) / (max - min);

  % Then scale to [x,y]:
  range2 = range(2) - range(1);
  normalized = (array*range2) + range(1);
endfunction
