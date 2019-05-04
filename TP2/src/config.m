dataset = dlmread("./terrain08.data")(2:end,:);
hidden_layers = [5, 5];
functions = {@tanh};
derivatives = {@tanhD};
eta = 0.01;
outputs = 1;
inputs = columns(dataset) - s;
epsilon = 0.1;