const mysql = require('mysql'); //se manda a llamar la dependencia de MYSQL

const mysqlConnection = mysql.createConnection({ //se configura la conexion a la BD
    host: 'localhost', //desde donde esta corriendo la BD
    user: 'admin', //usuario   
    password: 'admin', //contraseña
    database: 'proyecto', //nombre de la base de datos
    multipleStatements: true
});

mysqlConnection.connect(function(err) { //se hace la conexion y se comprueba que no haya errores
    if (err) {
        console.log(err); //si existe el error se imprime cual es
        return;
    } else {
        console.log('Data base connected');
    }
});

module.exports = mysqlConnection; //se exporta para poder usar en cualquier parte