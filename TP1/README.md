# Trabajo Práctico Especial N°1 - Sistemas de Inteligencia Artificial
## Grupo 8

## Compilation
Please check that maven is installed and `JAVA_HOME` is set accordingly.

```bash
git clone git@bitbucket.org:itba/sia-2019-1c-08.git
cd TP1/gps
mvn clean package
```

## Usage

```bash
alias fillzone='java -jar target/gps-1.0.jar'
fillzone # plus optional params
```

1. `fillzone` (runs with 5x5 random generated board)
2. `fillzone <filename>` (runs with board inside a file)
3. `fillzone <height> <width>` (runs with height * width random generated board)

### File format example:
```
3 5
35201
21043
20013
```
