config;
B = (-1) * ones(rows(dataset), 1);
M = [B dataset];
W = {};
V = {};

# Random weight initialization
W{1} = rand(hidden_layers(1), inputs + 1) - 0.5;
for i=1:columns(hidden_layers)-1
  W{i+1} = rand(hidden_layers(i + 1), hidden_layers(i) + 1) - 0.5;
endfor
W{end + 1} = rand(outputs, hidden_layers(end) + 1) - 0.5;

# Iterate until all patterns match calculated output with expected output
redo = true;
while(redo)
  # ...
  # O = randperm(rows(M));
  # redo = false;
  # num = num + 1;
  
  # while(!isempty(O))
  #  index = O(end);
  #  O(end) = [];
  #  V = {M(index,1:end-1)'};
  #  H = {W{1} * V{end}};
  #  V{end+1} = [-1; g(H{end})];
  #  H{end+1} = W{2} * V{end};
  #  V{end+1} = g(H{end});
  #  delta{3} = g_der(H{end}).*(M(index,end)-V{end});
    
  #  delta{2} = g_der(H{1}(1)) * dot(W{2}(:,2), delta{3});
  #  delta{2} = [delta{2}; g_der(H{1}(2)) * dot(W{2}(:,3), delta{3})];
    
  #  deltaW = n * delta{2} .* V{1}';
  #  W{1} = W{1} + deltaW;
    
  #  deltaW = n * delta{3} .* V{2}';
  #  W{2} = W{2} + deltaW;
    
  #  if(abs(V{end} - M(index,end)) > eps)
  #    redo = true;
  #  endif
  # endwhile
  
  if(abs(V{end} - M(index, end)) < epsilon)
    redo = false;
  endif
endwhile