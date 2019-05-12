# Configuration file

# Debug mode
DEBUG_MODE = true;
debug_on_interrupt(DEBUG_MODE);

# Data set
dataset = dlmread("terrain08.data")(2:end, :);

# Number of neurons for each hidden layer (array length = hidden layers count)
hidden_layers = [12, 12];

# Activation function index to use (1: hyperbolic tangent, 2: exponential)
fn_index = 2;

# Activation functions
functions = {@hyp_tan, @expn};

# Activation functions derivatives
derivatives = {@hyp_tan_derivative, @expn_derivative};

# Activation functions inverses
inverses = {@hyp_tan_inverse, @expn_inverse};

# Activation function beta value
beta = 1.0;

# Normalization ranges
normalization_ranges = [inverses{fn_index}(0.05, beta), inverses{fn_index}(0.95, beta); 
                        inverses{fn_index}(0.05, beta), inverses{fn_index}(0.95, beta); 
                        0.05, 0.95];
normalization_mins = min(dataset);
normalization_maxs = max(dataset);

# Number of outputs
outputs = 1;

# Percentage of patterns for training
patterns = 0.1;

# Optional input patterns for training
input_patterns = [];

# Max epochs
global max_epochs = -1;

# Allowed error
global epsilon = 0.01;

# Learning rate
global eta = 0.1;

# Weight optimization to use (0: Default, 1: Momentum, 2: Adam, 3: Adamax, 4: Nadam)
global weight_optimization = 2;

# Momentum params
global momentum_alpha = 0.9;

# Adam params
global adam_beta1 = 0.9;
global adam_beta2 = 0.999;
global adam_epsilon = 1e-8;

# Adaptative eta params
global adaptative_eta = false;
global eta_a = 0.0001;
global eta_b = 0.05;
global eta_min = 0.0005;
global eta_max = 0.1;
global epoch_min_reduction_steps = 3;