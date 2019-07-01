const express = require('express');
const router = express.Router();

const mysqlConnection  = require('../database.js');

// GET all users
router.get('/', (req, res) => {
  mysqlConnection.query('SELECT * FROM users', (err, rows, fields) => {
    if(!err) {
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
    if(!err) {
      res.json({status: 'user Deleted'});
    } else {
      console.log(err);
    }
  });
});

// INSERT An Employee
router.post('/', (req, res) => {
  const {id, name, email, password} = req.body;
  console.log(id, name, email, password);
  const query = `
    SET @id = ?;
    SET @name = ?;
    SET @email = ?;
    SET @password = ?;

    CALL userAddOrEdit(@id, @name, @email, @password);
  `;
  mysqlConnection.query(query, [id, name, email, password], (err, rows, fields) => {
    if(!err) {
      res.json({status: 'user Saved'});
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
    if(!err) {
      res.json({status: 'user Updated'});
    } else {
      console.log(err);
    }
  });
});

module.exports = router;