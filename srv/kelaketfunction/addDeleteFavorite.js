module.exports={
    adddeletefavorite:function (req,resp) {
        var connectdb=require("../basefunction/connectDb")
        var existenceuser=require("../basefunction/existenceUser")
        var con=connectdb.con()
        con.connect()
        console.log("connected!!")
        existenceuser.existenceUser(con,req,function (returnvalue) {

            if(returnvalue)
            {
                if(req.body['add']==1) {
                    var query = `INSERT INTO addfavorite (f_id,u_id) VALUES ('${req.body['f_id']}','${req.body['u_id']}')`
                    con.query(query, function (err) {
                        if (err)
                            throw err
                        console.log("add favorite")
                        resp.setHeader('Content-Type', 'application/json');
                        resp.send({status: true})
                    })
                }else
                {
                    var query = `Delete From addfavorite where u_id='${req.body['u_id']}' and f_id='${req.body['f_id']}'`
                    con.query(query, function (err) {
                        if (err)
                            throw err
                        console.log("delete favorite")
                        resp.setHeader('Content-Type', 'application/json');
                        resp.send({status: true})
                    })
                }
            }
            else{
                console.log("user not defined")
                resp.setHeader('Content-Type', 'application/json');
                resp.send({status:false})
            }

        })

    }
}