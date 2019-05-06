function y = expnD(x, beta)
  y = 2 * beta * expn(x, beta) * (1 - expn(x, beta));
endfunction
