module.exports={
    addscore:function (req,resp) {
        var connectdb=require("../basefunction/connectDb")
        var con=connectdb.con()
        con.connect()
        console.log("connected!!")

        var sqlquery=`INSERT INTO score (f_id,u_id) VALUES ('${req.body['f_id']}','${req.body['u_id']}')`
        console.log(sqlquery)
        con.query(sqlquery,function (err,result,field) {
            if(err)
                throw err

        })
    }
}