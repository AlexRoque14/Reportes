const express = require('express'); //se solicita el servicio de Express
const app = express(); //se utiliza el servicio 
const morgan = require('morgan');
const bodyParser = require('body-parser');
var cors = require('cors');

//settings
app.set('port', process.env.PORT || 3000); //encaso de estar definido un servicio lo tomará, si no creara uno en el puerto 3000.
app.set('json spaces', 2);

//middleware
app.use(morgan('dev')); //imprime informacion acerca del servidor 
app.use(express.urlencoded({ extended: false }));
app.use(express.json()); //para crear y recibir archivos Json

//cors
app.use(cors());
app.use(function(req, res, error, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    next();
  });

  app.use((req, res, next) => {
    res.header('Access-Control-Allow-Origin', '*');
    res.header('Access-Control-Allow-Headers', 'Authorization, X-API-KEY, Origin, X-Requested-With, Content-Type, Accept, Access-COntrol-Allow-Request-Method');
    res.header('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, DELETE');
    res.header('Allow', 'GET, POST, OPTIONS, PUT, DELETE');
    next();
});


//routes
app.use('/api/users',require('./routes/users')); //pedimos la ruta que esta en la carpeta de routes
app.use('/api/report',require('./routes/report'));
//starting the server
//se corre con "npm run dev". 
app.listen(3000, () => {
    console.log(`Server on port ${app.get('port')}`); //Mensaje al iniciar el servidor 
});