# Configuration file

# Debug mode
DEBUG_MODE = true;
debug_on_interrupt(DEBUG_MODE);
global stop_now = false;
dbstop in training_batch at 51 if stop_now == true;
dbstop in training_incremental at 53 if stop_now == true;


# Data set
dataset = dlmread("terrain08.data")(2:end, :);

# Number of neurons for each hidden layer (array length = hidden layers count)
hidden_layers = [12 12];

# Activation function index to use (1: hyperbolic tangent, 2: exponential)
fn_index = 2;

# Activation functions
functions = {@hyp_tan, @expn};

# Activation functions derivatives
derivatives = {@hyp_tan_derivative, @expn_derivative};

# Activation functions inverses
inverses = {@hyp_tan_inverse, @expn_inverse};

# Activarion functions normalization ranges
functions_min = {-0.93, 0.05};
functions_max = {0.93, 0.95};

# Activation function beta value
beta = 1.0;

# Normalization ranges
normalization_ranges = [inverses{fn_index}(functions_min{fn_index}, beta), inverses{fn_index}(functions_max{fn_index}, beta); 
                        inverses{fn_index}(functions_min{fn_index}, beta), inverses{fn_index}(functions_max{fn_index}, beta); 
                        functions_min{fn_index}, functions_max{fn_index}];
normalization_mins = min(dataset);
normalization_maxs = max(dataset);

# Number of outputs
outputs = 1;

# Percentage of patterns for training
patterns = 0.5;

# Optional input patterns for training
input_patterns = [];

# Generalization epsilon
gen_epsilon = 0.05;

# Max epochs
global max_epochs = 500;

# Allowed error
global epsilon = 0.01;

# Learning rate
global eta = 0.01;

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