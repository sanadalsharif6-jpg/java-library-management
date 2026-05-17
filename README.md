# Java Library Management Pro

An object-oriented Java library management system.

## Features

- Book management
- Member management
- Borrow and return system
- Active loan tracking
- Search books
- CSV file persistence
- Package-based Java structure

## Compile

From the project folder:

```bash
javac -d out src/Main.java src/model/*.java src/service/*.java
```

## Run

```bash
java -cp out Main
```

## Publish to GitHub

```bash
git init -b main
git add .
git commit -m "Initial commit"
git remote add origin YOUR_REPOSITORY_URL
git push -u origin main
```
