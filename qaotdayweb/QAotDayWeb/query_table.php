<?php
function good_query($string, $debug=0)
{
    if ($debug == 1)
        print $string;
    if ($debug == 2)
        error_log($string);

    $result = mysql_query($string);

    if ($result == false)
    {
        error_log("SQL error: ".mysql_error()."\n\nOriginal query: $string\n");
        // Remove following line from production servers 
        die("SQL error: ".mysql_error()."\b<br>\n<br>Original query: $string \n<br>\n<br>");
    }
    return $result;
}

function good_query_table($sql, $debug=0)
{
    // this function require presence of good_query() function
    $result = good_query($sql, $debug);
    
    $table = array();
    if (mysql_num_rows($result) > 0)
    {
        $i = 0;
        while($table[$i] = mysql_fetch_assoc($result)) 
            $i++;
        unset($table[$i]);                                                                                  
    }                                                                                                                                     
    mysql_free_result($result);
    return $table;
}
?>