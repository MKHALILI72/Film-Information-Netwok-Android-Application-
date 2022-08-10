module.exports={
    sendemail:function (req,resp) {

        var connectdb=require("../basefunction/connectDb")
        var existenceuser=require("../basefunction/existenceUser")
        var getEmailPass=require("../basefunction/getEmailPass")
        var con=connectdb.con()
        con.connect()
        console.log("connected!!")
        existenceuser.existenceUser(con,req,function (returnvalue) {
            getEmailPass.getemailpass(con,req,function (returnvalue2) {
                if(returnvalue){
                    var nodemailer=require('nodemailer')
                    var transporter = nodemailer.createTransport({
                        service: 'gmail',
                        auth: {
                            user: 'kelaket2@gmail.com',
                            pass: 'Kelaket123456789'
                        }
                    });
                    var mailOptions = {
                        from: 'kelaket2@gmail.com',
                        subject: 'یادآوری رمز عبور(پشتیبانی کلاکت)',
                        to:returnvalue2.useremail,
                        text:'با عرض سلام و احترام\n' +
                            'رمز عبور شما:\n' +
                            '\n'+returnvalue2.password

                    };

                    transporter.sendMail(mailOptions, function(error, info){
                        if (error) {
                            console.log(error);
                        } else {
                            console.log('Email sent: ' + info.response);
                            resp.setHeader('Content-Type', 'application/json');
                            resp.send({status:true})
                        }
                    });
                }else{
                    console.log("user not defined")
                    resp.setHeader('Content-Type', 'application/json');
                    resp.send({status:false})
                }
            })





        })

    }
}