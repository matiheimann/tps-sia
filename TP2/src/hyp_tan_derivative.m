function y = hyp_tan_derivative(x, beta)
  y = beta * (1 - tanh(x).^2);
end