package com.example.pc.myapplicationmapaprueba;

/**
 * Created by pc on 29/02/2016.
 */

import org.ksoap2.serialization.Marshal;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class MarshallArray implements Marshal {
    //this method doesn't work yet
    public Object readInstance(XmlPullParser parser, String namespace, String name, PropertyInfo expected)
            throws IOException, XmlPullParserException {
        return parser.nextText();
    }

    public void register(SoapSerializationEnvelope cm) {
        cm.addMapping(cm.xsd, "String[][]", String[][].class, this);
    }

    public void writeInstance(XmlSerializer writer, Object obj) throws IOException {
        String[][] myArray = (String[][]) obj;
        for (int i = 0; i < myArray.length; i++)
        {
            writer.startTag("", "ArrayOfArrayOfString");
            for (int j = 0; j < myArray[i].length; j++)
            {

                writer.startTag("", "string");
                writer.text(myArray[i][j]);
                writer.endTag("", "string");
            }


            writer.endTag("", "ArrayOfArrayOfString");
        }
    }
}
