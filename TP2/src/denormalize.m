function denormalized = denormalize(array, range, min, max)
  range2 = range(2) - range(1);
  array = (array-range(1)) / range2;
  
  denormalized = array * (max - min) + min;
endfunction