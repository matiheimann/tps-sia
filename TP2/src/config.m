#Configuration file

# Data set
dataset = dlmread("terrain08.data")(2:end, :);

# Number of neurons for each hidden layer (array length = hidden layers count)
hidden_layers = [12, 12];

# Activation functions for each layer
functions = {@tanh, @tanh, @tanh};

# Activation functions derivatives for each layer
derivatives = {@tanhD, @tanhD, @tanhD};

# Number of outputs
outputs = 1;

# Percentage of patters for training
patterns = 0.1;

# Optional input patterns for training
input_patterns = [1, 2];

# Learning rate
eta = 0.01;

# Allowed error
epsilon = 0.1;

# Momentum
momentum_alpha = 0.9;