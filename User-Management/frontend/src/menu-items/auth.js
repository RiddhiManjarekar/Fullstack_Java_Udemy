// assets
import { LoginOutlined, ProfileOutlined, LogoutLined } from '@ant-design/icons';

// icons
const icons = {
  LoginOutlined,
  ProfileOutlined
};
// const isLoginEnabled=true
const isLoginEnabled=localStorage.getItem('token');

const caseLogin=[{
  id:'logout1',
  title:'Logout',
  type:'item',
  url:'/logout',
  icon:icons.LogoutOutlined,
  target:true
}]

const caseLogout=[{
  id:'Login',
  title:'Login',
  type:'item',
  url:'/login',
  icon:icons.LogoutOutlined,
  target:true,
},
{
  id:'register1',
  title:'Register',
  type:'item',
  url:'/register',
  icon:icons.ProfileOutlined,
  target:true,
}]


const auth = {
  id: 'authentication',
  title: 'Authentication',
  type: 'group',
  children:[
    isLoginEnabled
    && caseLogin[0],
    !isLoginEnabled
    && caseLogout[0],
    !isLoginEnabled
    && caseLogout[1],

  ].filter(Boolean) //Remove falsy values (pages with condition false)
  };
  // children: [
  //   //Conditionally include the Login page
  //   isLoginEnabled ?
  //   {
  //     id: 'login1',
  //     title: 'Login',
  //     type: 'item',
  //     url: '/login',
  //     icon: icons.LoginOutlined,
  //     target: true
  //   }
  //   : //Else condition
  //   {
  //     id: 'disabledLogin',
  //     title: 'Login (Disabled)',
  //     type: 'item',
  //     url: '/disabled-login',
  //     icon: icons.LoginOutlined,
  //     target: true,
  //     disabled:true  // Add any specific properties for the disabled state
  //   },
  //   {
  //     id: 'register1',
  //     title: 'Register',
  //     type: 'item',
  //     url: '/register',
  //     icon: icons.ProfileOutlined,
  //     target: true,
  //     disabled:true  // Add any specific properties for the disabled state
  //   }
  // ]


export default auth;
