#Configuration file

# Data set
dataset = dlmread("terrain08.data")(2:end, :);

# Number of neurons for each hidden layer (array length = hidden layers count)
hidden_layers = [12, 12];

# Activation function index to use (1: hyperbolic tangent, 2: exponential)
fn_index = 1;

# Activation functions for each layer
functions = {@hyp_tan, @expn};

# Activation functions derivatives for each layer
derivatives = {@hyp_tanD, @expnD};

# Activation function beta value
beta = 1;

# Number of outputs
outputs = 1;

# Percentage of patterns for training
patterns = 0.25;

# Optional input patterns for training
input_patterns = [1, 16];

# Learning rate
eta = 0.01;

# Allowed error
epsilon = 0.1;

# Momentum
momentum_alpha = 0.9;

# Normalization ranges
normalization_ranges = [-1, 1; -1, 1; -1, 1];
