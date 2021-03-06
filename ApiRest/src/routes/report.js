const express = require('express');
const router = express.Router();
const mysqlConnection  = require('../database.js');



// GET all users
router.get('/', (req, res) => {
  mysqlConnection.query('SELECT * FROM report', (err, rows, fields) => {
    if(!err) {
      res.json(rows);
    } else {
      console.log(err);
    }
  });  
});


// GET An user for id
router.get('/:user_id', (req, res) => {
  const { user_id } = req.params; 
  mysqlConnection.query('SELECT * FROM report WHERE user_id = ?', [user_id], (err, rows, fields) => {
    if (!err) {
      res.json(rows);
    } else {
      console.log(err);
    }
  });
});

//get an user for title
router.get('/:title', (req, res) => {
  const { id } = req.params; 
  mysqlConnection.query('SELECT * FROM report WHERE title = ?', [titel], (err, rows, fields) => {
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
  mysqlConnection.query('DELETE FROM report WHERE id = ?', [id], (err, rows, fields) => {
    if(!err) {
      res.json({status: 'Report Deleted'});
    } else {
      console.log(err);
    }
  });
});

// INSERT An report
router.post('/', (req, res) => {
  const {id, title, description,  image, user_id} = req.body;
  console.log(id, title, description, image, user_id);
  const query = `
    SET @id = ?;
    SET @title = ?;
    SET @description = ?;
    SET @image = ?;
    SET @user_id = ?;
    CALL reportAddOrEdit(@id, @title, @description,  @image, @user_id);
  `;
  mysqlConnection.query(query, [id, title, description,image, user_id], (err, rows, fields) => {
    if(!err) {
      res.json({status: 'Report Saved'});
    } else {
      console.log(err);
    }
  });

});

router.put('/:id', (req, res) => {
  const { title, description, image, user_id} = req.body;
  const { id } = req.params;
  const query = `
    SET @id = ?;
    SET @title = ?;
    SET @description = ?;
    SET @image = ?;
    SET @user_id = ?;

    CALL reportAddOrEdit(@id, @title, @description,  @image, @user_id);
  `;
  mysqlConnection.query(query, [id, title, description, image, user_id], (err, rows, fields) => {
    if(!err) {
      res.json({status: 'report Updated'});
    } else {
      console.log(err);
    }
  });
});



module.exports = router;