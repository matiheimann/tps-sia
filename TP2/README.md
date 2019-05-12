# Trabajo Práctico Especial N°1 - Sistemas de Inteligencia Artificial
## Grupo 8

## Configuration

To run the project, GNU Octave is required. Versions 4.2.2 and 5.1.0 were tested in multiple Operating Systems.

```bash
git clone git@bitbucket.org:itba/sia-2019-1c-08.git
cd sia-2019-1c-08/TP2/src
vim config.m # to tweak all configuration values
```

By default, only 10% of the patterns in the dataset will be used for training the neural network. This can be modified by changing the `patterns` variable in `config.m`. If very specific patterns need to be used for the training, the `input_patterns` variable can be used. This array should only contain indexes that represent lines of given dataset file. Example:

```
# terrain08.data file
      x1        x2         z
   -2.4584   -2.5350    0.4427
    2.8433   -2.3063    0.7474
    1.5835    2.2574   -0.0096
    2.0622   -0.9405    0.3896
    2.4630    0.6573   -0.4745
   -1.5536         0         0
```

If the `(2.0622, -0.9405, 0.3896)` and the `(2.8433, -2.3063, 0.7474)` patterns wish to be used during the training, `input_patterns` must be set like so:
`input_patterns = [4, 2]`

Patterns will be sorted randomly before each training.

*NOTE:* Variables defined in the `config.m` file should not be removed. Their values should be changed as instructed in the comments placed above each variable. 

## Usage

*NOTE:* The debug mode does not work when using Windows or macOS. In these operating systems, the execution is stopped when hitting `Ctrl+C` regardless of the configuration.

```bash
pwd # Make sure this is the sia-2019-1c-08/TP2/src folder
octave-cli
weights = training_batch; # or training_incremental
neural_network(weights); # to test the trained network
```

Break points have been placed at lines of interest, but will not trigger unless the variable `stop_now` is set to true. This can be done at runtime at any given time. It can be achieved by following these steps:

1) Hit `Ctrl+C` when ready
2) Type in the following commands, one at a time:
```
global stop_now
stop_now
stop_now = true
dbcont # continue the execution
```
3) The program will automatically stop when the break point is reached. Changes can be made safely at said break points.
4) After making the changes, type in the following commands:
```
stop_now = false
dbcont # continue the execution
```
