# eve-sde-slick
To generate tables:
```
cp workspace.env.def workspace.env
Edit workspace.env with actual values to connect to a db
source workspace.env
sbt genTables
```

To test build:
`sbt publishLocal`
