declare function local:orderByName($data as element(sl)){

    let $result :=  
        for $station in $data/si order by (replace($station//@na/string(), "[0-9]{3}(\s)","")) 
            return $station 
    return $result
};
declare function local:orderByTotal($data as element(sl)){
    let $result :=  
        for $station in $data/si order by number($station/@to)
            return $station 
    return $result
};
declare function local:orderByDispoFaible($data as element(sl)){

    let $result :=  
        for $station in $data/si
            return if ($station/@fr < 0.3*$station/@to) 
            then $station
            else ()
    return $result
};
declare function local:orderByDispoMoyen($data as element(sl)){

    let $result :=  
        for $station in $data/si
            return if ($station/@fr > 0.3*$station/@to and $station/@fr < 0.6*$station/@to) 
            then $station
            else ()
    return $result
};
declare function local:orderByDispoHaut($data as element(sl)){

    let $result :=  
        for $station in $data/si
            return if ($station/@fr > 0.6*$station/@to) 
            then $station
            else ()
    return $result
};
let $byName := local:orderByName(//sl)
let $byTotal := local:orderByTotal(//sl)
let $byDispoFaible := local:orderByDispoFaible(//sl)
let $byDispoMoyen := local:orderByDispoMoyen(//sl)
let $byDispoHaut := local:orderByDispoHaut(//sl)


return <body>
            <table>
                <thead>
                    <tr>
                        <th>Station</th>
                        <th>Latitude</th>
                        <th>Longitude</th>
                        <th>Occupés</th>
                        <th>Disponibles</th>
                        <th>Nombre de places total</th>
                    </tr>
                </thead>
                <tbody>
                    {
                        for $station in $byName return
                        <tr>
                            <td>{$station/@na/string()}</td>
                            <td>{$station/@la/string()}</td>
                            <td>{$station/@lg/string()}</td>
                            <td>{$station/@av/string()}</td>
                            <td>{$station/@fr/string()}</td>
                            <td>{$station/@to/string()}</td>
                        </tr>
                    }
                </tbody>
            </table>
            <h1> Nbr total de places </h1>
            <table>
                <thead>
                    <tr>
                        <th>Station</th>
                        <th>Latitude</th>
                        <th>Longitude</th>
                        <th>Occupés</th>
                        <th>Disponibles</th>
                        <th>Nombre de places total</th>
                    </tr>
                </thead>
                <tbody>
                    {
                        for $station in $byTotal return
                        <tr>
                            <td>{$station/@na/string()}</td>
                            <td>{$station/@la/string()}</td>
                            <td>{$station/@lg/string()}</td>
                            <td>{$station/@av/string()}</td>
                            <td>{$station/@fr/string()}</td>
                            <td>{$station/@to/string()}</td>
                        </tr>
                    }
                </tbody>
            </table>

            <h1> Par disponibilité </h1>
            <table>
                <thead>
                    <tr>
                        <th>Station</th>
                        <th>Latitude</th>
                        <th>Longitude</th>
                        <th>Occupés</th>
                        <th>Disponibles</th>
                        <th>Nombre de places total</th>
                    </tr>
                </thead>
                <tbody>
                    {
                        for $station in $byDispoFaible return
                        <tr>
                            <td class="faible">{$station/@na/string()}</td>
                            <td class="faible">{$station/@la/string()}</td>
                            <td class="faible">{$station/@lg/string()}</td>
                            <td class="faible">{$station/@av/string()}</td>
                            <td class="faible">{$station/@fr/string()}</td>
                            <td class="faible">{$station/@to/string()}</td>
                        </tr>
                    }
                    {
                        for $station in $byDispoMoyen return
                        <tr>
                            <td class="moyen">{$station/@na/string()}</td>
                            <td class="moyen">{$station/@la/string()}</td>
                            <td class="moyen">{$station/@lg/string()}</td>
                            <td class="moyen">{$station/@av/string()}</td>
                            <td class="moyen">{$station/@fr/string()}</td>
                            <td class="moyen">{$station/@to/string()}</td>
                        </tr>
                    }
                    {
                        for $station in $byDispoHaut return
                        <tr>
                            <td class="haut">{$station/@na/string()}</td>
                            <td class="haut">{$station/@la/string()}</td>
                            <td class="haut">{$station/@lg/string()}</td>
                            <td class="haut">{$station/@av/string()}</td>
                            <td class="haut">{$station/@fr/string()}</td>
                            <td class="haut">{$station/@to/string()}</td>
                        </tr>
                    }
                </tbody>
            </table>
    </body>