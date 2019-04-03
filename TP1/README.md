# Trabajo Práctico Especial N°1 - Sistemas de Inteligencia Artificial
## Grupo 8

## Compilation
Please check that maven is installed and `JAVA_HOME` is set accordingly.

```bash
git clone git@bitbucket.org:itba/sia-2019-1c-08.git
cd TP1/gps
mvn clean
mvn package -Pgps
mvn package -Pfillzone
```

## Usage

```bash
alias fillzone='java -jar target/fillzone.jar'
fillzone # plus optional params
```
<searchStrategy> = <all|bfs|dfs|iddfs|greedy|astar>
1. `fillzone <searchStrategy>` (runs with 5x5 random generated board)
2. `fillzone <searchStrategy> <filename>` (runs with board inside a file)
3. `fillzone <searchStrategy> <height> <width>` (runs with height * width random generated board)

### File format example:
```
3 5
35201
21043
20013
```
