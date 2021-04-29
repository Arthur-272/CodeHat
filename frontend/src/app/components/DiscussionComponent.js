import React, { useState } from 'react';
import { CKEditor } from '@ckeditor/ckeditor5-react';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import { Button, Form } from 'reactstrap';

function DiscussionComponent() {
    const [text, setText] = useState("");
    const submitButtonStyle = {
        fontSize: "20px",
        backgroundColor: "indigo",
        color: "white",
        padding: "auto",
        marginTop: "15px",
    }

    return (
    
        <div className="col-10 main-panel" >
            <div>
                <div className="editor">
                    <CKEditor
                        editor={ClassicEditor}
                        data={text}
                        onChange={(event, editor) => {
                            const data = editor.getData()
                            setText(data)
                        }}
                    />
                </div>
                <div>
                <Form>
                <Button style={submitButtonStyle} type="submit">Submit</Button>
                    </Form>
                    
                </div>
 
            </div>
        </div>

    )
}
export default DiscussionComponent;

