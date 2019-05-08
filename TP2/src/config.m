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

# Max epochs
max_epochs = -1;

# Allowed error
epsilon = 0.1;

# Learning rate
eta = 0.01;

# Momentum params
momentum_alpha = 0.9;

# Adaptative eta params
eta_a = 0.001;
eta_b = 0.1;
eta_min = 0.01;
epoch_min_reduction_steps = 5;

# Normalization ranges
normalization_ranges = [-1.7, 1.7; -1.7, 1.7; -tanh(1.7), tanh(1.7)];
