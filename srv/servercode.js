var express=require('express')
var buffer = require('buffer');
var path = require('path');
var fs = require('fs');
var app=express()
var bodyp=require('body-parser')
var sql=require('mysql')
var multer=require('multer')
app.use(bodyp.urlencoded({limit:'50mb',extended:"true"}))
app.use(bodyp.json({limit: '50mb'}))

app.post('/',function (req,resp) {

    var showmassage={
        projectname:"mohammadkhalili"
    }

    resp.send(showmassage);

})



app.post('/registerUser',function (req,resp) {

    var registeruser=require("./kelaketfunction/registerUser")
    registeruser.registerUser(req,resp)
})

app.post('/login',function (req,resp) {

    var registeruser=require("./kelaketfunction/Login")
    registeruser.login(req,resp)
})

app.post('/getListFilm',function (req,resp) {
    var getlistfilm=require('./kelaketfunction/getListFilm')
    getlistfilm.getListFilm(req,resp)
})




//functionname=addDeleteFavorite (addflag,email,password,filmid)-> return status false or true
app.post('/adddeletefavorite',function (req,resp) {
    var addDeleteFavorite=require('./kelaketfunction/addDeleteFavorite')
    addDeleteFavorite.adddeletefavorite(req,resp)
})
//functionname=sendEmail  email ->send password for user by email




//functionname=addComment (email,password,filmid,comment)->return status false or true
app.post('/addcomment',function (req,resp) {
    var addComment=require('./kelaketfunction/addComment')
    addComment.addcomment(req,resp)
})


//functionname=getListComment(email,password,filmid)->return status and (posters,usernames,comments)
app.post('/getlistcomment',function (req,resp) {
    var getListComment=require('./kelaketfunction/getListComment')
    getListComment.getlistcomment(req,resp)
})
 

//functionname=setRatefilm (email,password,filmid,rate)->addnvote&&changeratefilm return (status)
app.post('/setRateFilm',function (req,resp) {
    var setRateFilm=require('./kelaketfunction/setRateFilm')
       setRateFilm.setratefilm(req,resp)
})
//functionname=getfilminfo(meail,password,filmid)->status and whole film info
app.post('/getfilminfo',function (req,resp) {

    var getFilmInfo=require('./kelaketfunction/getFilmInfo')
    getFilmInfo.getfilminfo(req,resp)

})


app.post('/sendmail',function (req,resp) {
    var sendEMail=require('./kelaketfunction/sendEmail')
    sendEMail.sendemail(req,resp)
})

//functionname=editeuserinfo(email,password,username,password,description,imagepro) return true or false
app.post('/editeuserinfo',function (req,resp) {
    var editeUserInfo=require('./kelaketfunction/editeUserInfo')
    editeUserInfo.editeuserinfo(req,resp)
})
//functionname=getuserfavoritelist(email,password)returnn listfilm
app.post('/getuserfavoritelist',function(req,resp){
    var getUserFavoriteList=require('./kelaketfunction/getUserFavoriteList')
    getUserFavoriteList.getuserfavoritelist(req,resp)
})

app.post('/getuserinfobyid',function (req,resp) {
    var getUserInfoById=require('./kelaketfunction/getUserInfoById');
    getUserInfoById.getuserinfobyid(req,resp);

})
app.post('/upload',function (req,resp) {
        var base64str=req.body['base64str']
        var filename=req.email;
        var buf = Buffer.from(base64str,'base64');

    fs.writeFile(path.join('../proimg/',filename), buf, function(error){
        if(error){
            throw error;
        }else{
            console.log('File created from base64 string!');
            resp.setHeader('Content-Type', 'application/json');
            resp.send({status:true})
        }

})

})


app.listen(8080)


