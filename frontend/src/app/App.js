import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {BrowserRouter,Route, useParams,Switch,withRouter} from 'react-router-dom'
import './App.scss';

import Navbar from './components/Navbar';

import Footer from './components/Footer';
import Dashboard from './components/Dashboard';
import HomePage from './components/HomePage';
import { withTranslation } from "react-i18next";
import Leaderboard from './components/Leaderboard';
import Discussion from '../app/Discussion/Discussion';
import Submissions from '../app/Discussion/Submisssions';
import Classes from './general-pages/Classes';
import MainLogin from './components/MainLogin';
import MainSignUp from './components/MainSignUp';
import ProblemList from './general-pages/ProblemList';
import Faculty from './general-pages/Faculty';
import AddClass from './general-pages/faculty-pages/AddClass'
import Subject from './general-pages/faculty-pages/Subject'
import AssignmentReport from './general-pages/faculty-pages/AssignmentReport'
import SignleSubmission from './general-pages/faculty-pages/SingleSubmission'
import AddPost from './general-pages/faculty-pages/AddPost'
import AddAssignmentProblems from './general-pages/faculty-pages/AddAssignmentProblems'
import AddProblem from './components/AddProblem';
import AddTestCases from './components/AddTestCases';
import MainProblemPage from './components/MainProblemPage';
import SingleClass from './general-pages/SingleClass';


class App extends Component {

  render () {
    return (
      <div className="container-scroller">
        <BrowserRouter>
        <div className="container-fluid page-body-wrapper">
            <Switch>
              <Route path="/" component={HomePage} exact/>
              <Route path="/login" component={MainLogin} exact/>
              <Route path="/signup" component={MainSignUp} exact/>
              <Route path="/loginSuccess" component={Dashboard} exact/>
              <Route path="/classes" component={Classes} exact/>
              <Route path="/practice" component={ProblemList} exact/>
              <Route path="/addProblem" component={AddProblem} exact/>
              <Route path="/addTestCases" component={AddTestCases} exact/>
              <Route path="/faculty" component={Faculty} exact/>
              <Route path="/discussion" component={Discussion} exact/>
              <Route path="/submission" component={Submissions} exact/>
              <Route path="/problem" component={MainProblemPage} exact/>
              <Route path="/leaderboard" component ={Leaderboard} exact/>
              <Route path="/general-pages/faculty-pages/add-class" component={AddClass}/>
              <Route path="/general-pages/faculty-pages/subject/:subject" component={Subject}/>
              <Route path="/general-pages/faculty-pages/problem/:problem" component={AssignmentReport}/>
              <Route path="/general-pages/faculty-pages/viewSolution" component={SignleSubmission}/>
              <Route path="/addPost" component={AddPost} />
              <Route path="/addAssignmentProblems" component={AddAssignmentProblems} />
              <Route path="/general-pages/class/:classId" component={SingleClass}/>
            </Switch>
            </div>
            <Footer />
          </BrowserRouter>
        
      </div>
    );
  }
}

export default withTranslation() (withRouter(App));
