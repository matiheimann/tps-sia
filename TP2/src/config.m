#Configuration file

# Data set
dataset = dlmread("terrain08.data")(2:end, :);

# Number of neurons for each hidden layer (array length = hidden layers count)
hidden_layers = [24, 12];

# Activation function index to use (1: hyperbolic tangent, 2: exponential)
fn_index = 1;

# Activation functions
functions = {@hyp_tan, @expn};

# Activation functions derivatives
derivatives = {@hyp_tan_derivative, @expn_derivative};

# Activation functions inverses
inverses = {@hyp_tan_inverse, @expn_inverse};

# Activation function beta value
beta = 1.0;

# Normalization ranges
normalization_ranges = [-1.7, 1.7; -1.7, 1.7; functions{fn_index}(-1.7, beta), functions{fn_index}(1.7, beta)];
normalization_mins = min(dataset);
normalization_maxs = max(dataset);

# Number of outputs
outputs = 1;

# Percentage of patterns for training
patterns = 0.1;

# Optional input patterns for training
input_patterns = [1, 2, 3];

# Max epochs
max_epochs = -1;

# Allowed error
epsilon = 0.01;

# Learning rate
eta = 0.001;

# Momentum params
momentum_alpha = 0.9;

# Adaptative eta params
eta_a = 0.00001;
eta_b = 0.1;
eta_min = 0;
epoch_min_reduction_steps = 3;
