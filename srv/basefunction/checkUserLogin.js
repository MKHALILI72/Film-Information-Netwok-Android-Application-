module.exports={
    checkUserLogin:function (email,password,callback) {
        var connectdb=require("../basefunction/connectDb")
        var con=connectdb.con()
        con.connect()
        console.log("connected!!")

        var sqlquery=`select * from users where u_email='${email}' and u_password='${password}'`
        console.log(sqlquery)
        con.query(sqlquery,function (err,result,field) {
            if(err)
                throw err
            console.log(result)
            if(result.length==0)
                callback(false)
            else
                callback(true)
        })
    }
}