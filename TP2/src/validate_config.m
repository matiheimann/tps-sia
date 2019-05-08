function validate_config()
  config;
  
  if length(hidden_layers) <= 0
    error("Must have at least one hidden layer");
  endif
  
  if length(hidden_layers(hidden_layers <= 0)) > 0
    error("Each hidden layer must have a positive amount of neurons");
  endif
  
  if outputs <= 0
    error("At least one output required");
  endif
  
  if patterns <= 0 || patterns > 1
    error("Pattern input percentage must be in range (0,1]");
  endif
  
  if !isempty(input_patterns)
    if length(input_patterns(input_patterns <= 0)) > 0 || length(input_patterns(input_patterns > rows(dataset))) > 0
      error("Input patterns indexes must be in range [1,%d]", rows(dataset));
    endif
  
    if round(patterns * rows(dataset)) < length(input_patterns)
      error("Invalid amount of input patterns: must be < %d", rows(dataset));
    endif
  endif
  
  # Eta validation...
  
  if epsilon < 0
    error("Epsilon value must be positive");
  endif
  
  if momentum_alpha < 0 || momentum_alpha > 1
    error("Momentum alpha value must be in range (0,1)");
  endif
endfunction
