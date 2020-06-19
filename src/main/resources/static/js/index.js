
function logEvent(type, message, args){
    webix.message({ text:message, expire:1000 }); 

  }

var addBtn = webix.ui({   
  view:"button", css:"webix_primary",label:"Add User",
  type:"icon", icon:"fa fa-plus", inputWidth:100, id:"addBtn",
  click:function(id,event){
    newUserForm.show();
  }
});


var search = webix.ui({
  view:"search", id:"search",
  placeholder:"Search..",
  width: 300
});


var newUserForm = webix.ui({
  view:"window", modal:true, 
  height:500,
  width:300,
  left:50, top:50,
  head:"Add New User",
  body:{
    view: "form", id:"newUserForm",
    elements: [
      { view: "text", label: 'UserName', name: "userName" },
      { view: "text", label: 'Password', name: "password", },
      { view: "button", value: "Save", css:"webix_primary", id:"saveNewUserBtn", 
        click:function(){
          var form =  $$('newUserForm').getValues();
          var formUserName = form.userName;
          var formPassword = form.password;
          console.log(formPassword + formPassword);
          if (formUserName === "" || formPassword === "") 
            webix.message("Empty UserName or Password ")
          else {
            axios.post('/registrationFormModel',{
              userName : formUserName,
              password :formPassword
          })
          webix.message("Add user success")
          newUserForm.hide();
          $$("table").add({
            userName: formUserName,
            password:formPassword
          },0);
          }
        
          }},
      { view: "button", value: "Cancel", id:"cancelBtn",
        click:function(id){
            newUserForm.hide();
        }}
    ],
    elementsConfig: {
      labelPosition: "top",
    }
  }
});
 var data = webix.ajax().get("/getUserList").then(function(data){
	 json.parse(data)
 });
var main_table = webix.ui({
  view:"datatable", 
  id:'table', 
  data: webix.ajax().get("/getUserList"),
  columns:[
    { id:"roleName", header:"roleName",fillspace:true ,editor:"text"},
    { id:"listUsers[0].userName", header:"UserName",fillspace:true ,editor:"text"},
    // { id:"password", header:"Password", fillspace:true ,editor:"text"},
    // { id:"email", header:"Email",fillspace:true ,editor:"text"},
    // { id:"active", header:"Active", fillspace:true ,editor:"text"},
    // { template:"<div class='webix_icon fa fa-edit editBtn' id = 'editBtn' ></div>  <div class='webix_icon fa-trash-o deleteBtn' id = 'deleteBtn'  ></div> "  , width:100, header:'Action'  },
    { id:"Edit",template:"<span class='webix_icon fa fa-edit editBtn' id = 'editBtn'   ></span>", width:75 },
    { id:"Delete",template:"<span class='webix_icon fa-trash-o deleteBtn' id = 'deleteBtn'  ></span>", width:75 },
  ],
  autoheight:true, 
  scroll:false,
  editable:true,
  editaction:"none",
  onClick:{ 
    "deleteBtn":function(event, rowId, target){
      var selectedRow = $$("table").getItem(rowId);
      var userNameDelete = selectedRow.userName;
      webix.confirm("Are you sure", "confirm-warning")
    .then(function(result){
      if (result == true){
        axios.post('/deleteUser',userNameDelete);
        logEvent("click","Delete User: " + selectedRow.userName, arguments);
         $$("table").remove(rowId);
      }
    })      
      // return false;       // here it blocks the default behavior
    },
      "editBtn":async function(event, rowId, target){
        $$("table").showColumn("Select");
        var selectedRow = $$("table").getItem(rowId);
        console.log(selectedRow);
        logEvent("click","Select User: " + selectedRow.userName, arguments);
        
        await $$("table").editRow(rowId);
        await axios.post('editUser',{
          userName:selectedRow.userName, 
          password:selectedRow.password});
        
        // return false; // here it blocks the default behavior
    }
},
  
  // on:{
  //   onItemClick:function(){  logEvent("click","Cell clicked",arguments);  },
  //   onAfterSelect:function(){  logEvent("select:after","Cell selected",arguments);  },
  //   onCheck:function(){  logEvent("check","Checkbox",arguments);  },
  //   onAfterEditStart:function(){  logEvent("edit:afterStart","Editing started",arguments);  },
  //   onAfterEditStop:function(){  logEvent("edit:afterStop","Editing ended",arguments);  },
  // }
});



webix.ready( function(){

});