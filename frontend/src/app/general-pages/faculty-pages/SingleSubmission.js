import { Breadcrumb, BreadcrumbItem } from 'reactstrap';
import { MDBJumbotron, MDBContainer, MDBInput, MDBBtn } from "mdbreact";

import React, { Component } from 'react'


class SingleSubmission extends Component{
    render(){
        return (

            <div className="container w-75">
            <Breadcrumb tag="nav" listTag="div">
                <BreadcrumbItem tag="a" href="#">Home</BreadcrumbItem>
                <BreadcrumbItem tag="a" href="#">Class</BreadcrumbItem>
                <BreadcrumbItem tag="a" href="#">Assignment</BreadcrumbItem>
                <BreadcrumbItem active tag="span">Submission</BreadcrumbItem>
            </Breadcrumb>
            <h2 className="container font-weight-light">Submissions</h2>
            <div className="container">
                <br></br>
                <h4 className="font-weight-light">Radhey</h4>
            </div>
    
            <MDBJumbotron fluid>
                <MDBContainer>
                    <p className="lead">
                        <h2 className="font-weight-light">Code</h2>
                        Wikipedia is a free, multilingual open-collaborative online encyclopedia created and maintained by a community of volunteer contributors using a wiki-based editing system. Wikipedia is the largest general reference work on the Internet,[3] and one of the 15 most popular websites as ranked by Alexa; in 2021, it was ranked as the 13th most visited.[4][note 3] The project carries no advertisements and is hosted by the Wikimedia Foundation, an American non-profit organization funded mainly through donations, 80% of which are small donations from individual users.[6]
                        Wikipedia was launched on January 15, 2001, by Jimmy Wales and Larry Sanger; Sanger coined its name as a portmanteau of "wiki" and "encyclopedia".[7][8] Initially available only in English, versions in other languages were quickly developed. The English Wikipedia, with 6.3 million articles as of March 2021, is the largest of the 321 language editions. Combined, Wikipedia's editions comprise more than 56 million articles, and attract more than 17 million edits and more than 1.7 billion unique visitors per month.[9][10]
                        Wikipedia has been criticized for its uneven accuracy and for exhibiting systemic bias, particularly gender bias, with the majority of editors being male.[11] In 2006, Time magazine stated that the open-door policy of allowing anyone to edit had made Wikipedia the "biggest and perhaps the best encyclopedia in the world", and a testament to the vision of Jimmy Wales.[12] The project's reputation improved further in the 2010s, as it received praise for its unique structure, culture, and absence of commercial bias.[3][11] In 2018, Facebook and YouTube announced that they would help users detect fake news by suggesting links to related Wikipedia articles.[13]
                    </p>
                </MDBContainer>
            </MDBJumbotron>
            <br></br>
            <h4 className="font-weight-solid">Feedback</h4>
            <MDBInput type="textarea" rows="5" />
            <button className="btn btn-success btn-lg float-right" type="submit"><b>Submit</b></button>
        </div>        
        )
    }
    }
    


export default SingleSubmission
