#Configuration file

# Data set
dataset = dlmread("terrain08.data")(2:end, :);

# Number of neurons for each hidden layer (array length = hidden layers count)
hidden_layers = [4, 3];

# Activation functions for each layer
functions = {@tanh, @tanh, @tanh};

# Activation functions derivatives for each layer
derivatives = {@tanhD, @tanhD, @tanhD};

# Number of outputs
outputs = 1;

# Number of patters for training
patterns = 100;

eta = 0.01;
epsilon = 0.1;