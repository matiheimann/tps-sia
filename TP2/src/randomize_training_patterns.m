function [e, s] = randomize_training_patterns(dataset, patterns, input_patterns, outputs)
  if(!isempty(input_patterns))
    input_patterns = unique(input_patterns);
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
  
  return 
endfunction
