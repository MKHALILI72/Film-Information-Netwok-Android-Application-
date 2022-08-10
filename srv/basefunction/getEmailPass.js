module.exports={
    getemailpass:function (con,req,callback) {
        var useremail
        var password
        var query=`select * from users where u_email='${req.body['email']}'`
        con.query(query,function (err,result,field) {
            if(err)
                throw err
            var retrn={
                useremail:result[0].u_email,
                password:result[0].u_password
            }
            console.log(useremail)
            console.log(password)
            callback(retrn)
        })

    }
}