const express = require('express');
const router = express.Router();
const mysqlConnection = require('../database.js');
var crypto = require('crypto');
var uuid = require('uuid');
var jwt = require('jsonwebtoken');



// GET all users
router.get('/', (req, res) => {
  mysqlConnection.query('SELECT * FROM users', (err, rows, fields) => {
    if (!err) {
      res.json(rows);
    } else {
      console.log(err);
    }
  });
});

// GET An user for id
router.get('/:id', (req, res) => {
  const { id } = req.params;
  mysqlConnection.query('SELECT * FROM users WHERE id = ?', [id], (err, rows, fields) => {
    if (!err) {
      res.json(rows[0]);
    } else {
      console.log(err);
    }
  });
});

//get an user for name
router.get('/:name', (req, res) => {
  const { id } = req.params;
  mysqlConnection.query('SELECT * FROM users WHERE name = ?', [name], (err, rows, fields) => {
    if (!err) {
      res.json(rows[0]);
    } else {
      console.log(err);
    }
  });
});

// DELETE An user
router.delete('/:id', (req, res) => {
  const { id } = req.params;
  mysqlConnection.query('DELETE FROM users WHERE id = ?', [id], (err, rows, fields) => {
    if (!err) {
      res.json({ status: 'user Deleted' });
    } else {
      console.log(err);
    }
  });
});

// INSERT A user
router.post('/', (req, res) => {
  const { id, name, email } = req.body;
  console.log(id, name, email);
  var post_data = req.body;
  var plaint_password = post_data.password;
  var hash_data = saltHashPassword(plaint_password);
  var password = hash_data.passwordHash;
  var salt = hash_data.salt;
  const query = `
    SET @id = ?;
    SET @name = ?;
    SET @email = ?;
    SET @password = ?;
    SET @salt = ?;
    CALL userAddOrEdit(@id, @name, @email, @password, @salt);
  `;

  mysqlConnection.query(query, [id, name, email, password, salt], (err, rows, fields) => {
    if (!err) {
      res.json({ status: 'user Saved' });
    } else {
      console.log(err);
    }
  });

});


router.put('/:id', (req, res) => {
  const { name, email, password } = req.body;
  const { id } = req.params;
  const query = `
    SET @id = ?;
    SET @name = ?;
    SET @email = ?;
    SET @password = ?;
    CALL userAddOrEdit(@id, @name, @email, @password);
  `;
  mysqlConnection.query(query, [id, name, email, password], (err, rows, fields) => {
    if (!err) {
      res.json({ status: 'user Updated' });
    } else {
      console.log(err);
    }
  });
});


router.post('/login/', (req, res, next) => {
  var post_data = req.body;
  var user_password = post_data.password;
  var name = post_data.name;

  mysqlConnection.query('SELECT * FROM users WHERE name = ?', [name], function (err, result, fields) {
    mysqlConnection.on('Error', function (err) {
      console.log('MySQL ERROR]', err);
      res.json("Login error", err);
    });

    console.log(result[0]);
    if (result && result.length) {
      var salt = result[0].salt;
      var encrypted_password = result[0].password;
      var hashed_password = checkHashPassword(user_password, salt).passwordHash;

        if (encrypted_password == hashed_password) {
          res.end(JSON.stringify(result[0]))
          console.log("awebo que funciona, tu lo hiciste guapo");
        }
        else {
          res.end(JSON.stringify('Wrong password'));
        }
      }
    else {
      res.json("User not exist");
    }
  });

});


//crypto password
var genRandomString = function (length) {
  return crypto.randomBytes(Math.ceil(length / 2))
    .toString('hex')  /*convert to hexa format*/
    .slice(0, length); /*return required number of characters*/
}

var sha512 = function (password, salt) {
  var hash = crypto.createHmac('sha512', salt);
  hash.update(password);

  var value = hash.digest('hex');
  return {
    salt: salt,
    passwordHash: value
  };
};
var saltHashPassword = function (userPassword) {
  var salt = genRandomString(16);
  var passwordData = sha512(userPassword, salt);
  return passwordData;
}

function checkHashPassword(userPassword, salt) {
  var passwordData = sha512(userPassword, salt);
  return passwordData;
}
module.exports = router;