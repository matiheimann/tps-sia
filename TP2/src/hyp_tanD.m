function y = hyp_tanD(x, beta)
  y = beta * (1 - tanh(x).^2);
end