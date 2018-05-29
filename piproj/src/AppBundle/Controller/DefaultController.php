<?php

namespace AppBundle\Controller;

use CMEN\GoogleChartsBundle\GoogleCharts\Charts\Histogram;
use CMEN\GoogleChartsBundle\GoogleCharts\Charts\PieChart;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;

class DefaultController extends Controller
{
    /**
     * @Route("/admin/dashboard", name="homepage")
     */
    public function indexAction(Request $request)

    {
        $stat_users = array();
        $em = $this->getDoctrine()->getManager();
        $conn = $this->get('database_connection');
        $statement = $conn->prepare('SELECT COUNT(DISTINCT id) as content ,DATE_FORMAT(created_at, "%M %d %Y") as content2 FROM user GROUP BY id');
        $statement->execute();
        $registerstat = $statement->fetchAll();


        $statement = $conn->prepare('
            SELECT COUNT(idPub) as nb, (COUNT(idPub)*10)/100 as pr, "Aide" as type FROM aide 
            UNION SELECT COUNT(`id_Pub`)as nb, (COUNT(id_Pub)*10)/100 as pr, "Colocation" as type from colocation 
            UNION SELECT COUNT(`id_Pub`) as nb, (COUNT(id_Pub)*10)/100 as pr, "Covoiturage" as type from covoiturage 
            UNION SELECT COUNT(`id_Pub`) as nb, (COUNT(id_Pub)*10)/100 as pr, "Evenement" as type from evenement
         ');

        $statement->execute();
        $countPubstat = $statement->fetchAll();

        //var_dump($countPubstat);


        $statement = $conn->prepare('
            SHOW GLOBAL STATUS LIKE "%memory%"
         ');

        $statement->execute();
        $memstat = $statement->fetchAll();


        $statement = $conn->prepare('
            SELECT COUNT(id_com) as content FROM commentaire
         ');

        $statement->execute();
        $comstat = $statement->fetchAll();


        $statement = $conn->prepare('
            SELECT COUNT(id_pub) as content FROM question WHERE etat = 2
        ');

        $statement->execute();
        $qstAttentetat = $statement->fetchAll();


        $statement = $conn->prepare('
            SELECT COUNT(id) as content FROM user 
WHERE YEAR(created_at) = YEAR(CURRENT_DATE()) AND 
      MONTH(created_at) = MONTH(CURRENT_DATE()); 
        ');

        $statement->execute();
        $userMounth = $statement->fetchAll();


        $statement = $conn->prepare('
            SELECT `username`,DATE_FORMAT(updated_at, "%M %d %Y") as date,`profile_picture` as content FROM user 
WHERE YEAR(`last_login`) = YEAR(CURRENT_DATE()) AND 
      MONTH(`last_login`) = MONTH(CURRENT_DATE()) limit 8;
        ');

        $statement->execute();
        $latestuser = $statement->fetchAll();

        // implementing charts

        $pieChart = new PieChart();
        $pieChart->getData()->setArrayToDataTable(
            [
                ['Language', 'Speakers (in millions)'],
                ['German', 5.85],
                ['French', 1.66],
                ['Italian', 0.316],
                ['Romansh', 0.0791]
            ]
        );
        $pieChart->getOptions()->setPieSliceText('label');
        $pieChart->getOptions()->setTitle('Swiss Language Use (100 degree rotation)');
        $pieChart->getOptions()->setPieStartAngle(100);
        $pieChart->getOptions()->setHeight(500);
        $pieChart->getOptions()->setWidth(900);
        $pieChart->getOptions()->getLegend()->setPosition('none');

        $histogram = new Histogram();
        $histogram->getData()->setArrayToDataTable([
            ['Population'],
            [12000000],
            [13000000],
            [100000000],
            [1000000000],
            [25000000],
            [600000],
            [6000000],
            [65000000],
            [210000000],
            [80000000],
        ]);
        $histogram->getOptions()->setTitle('Country Populations');
        $histogram->getOptions()->setWidth(900);
        $histogram->getOptions()->setHeight(500);
        $histogram->getOptions()->getLegend()->setPosition('none');
        $histogram->getOptions()->setColors(['#e7711c']);
        $histogram->getOptions()->getHistogram()->setLastBucketPercentile(10);
        $histogram->getOptions()->getHistogram()->setBucketSize(10000000);


        /*
                var_dump($userMounth);
                var_dump($qstAttentetat);
                var_dump($comstat);
                var_dump($memstat);
                var_dump($registerstat);
                var_dump($countPubstat);
        */

        return $this->render('@App/AdminCharts/chart.html.twig', [
            'base_dir' => realpath($this->getParameter('kernel.project_dir')) . DIRECTORY_SEPARATOR,
            'piechart' => $pieChart,
            'histogram' => $histogram,
            'usermounth' => $userMounth,
            'qstAttentetat' => $qstAttentetat,
            'comstat' => $comstat,
            'memstat' => $memstat,
            'registerstat' => $registerstat,
            'countPubstat' => $countPubstat,
            'latestuser' => $latestuser
        ]);

        return $this->render('AppBundle::index.html.twig', array('piechart' => $piechart, 'histogram' => $histogram));


        // replace this example code with whatever you need
        return $this->render(':default:dashboard.html.twig', [
            'base_dir' => realpath($this->getParameter('kernel.project_dir')) . DIRECTORY_SEPARATOR,
            'usermounth' => $userMounth,
            ''
        ]);
    }

    /**
     * @Route("/admin/users", name="users")
     */
    public function UsersAction(Request $request)
    {
        // replace this example code with whatever you need
        return $this->render(':default:dashboard.html.twig', [
            'base_dir' => realpath($this->getParameter('kernel.project_dir')) . DIRECTORY_SEPARATOR,
        ]);
    }


    /**
     * @Route("/acceuil/init", name="mobilehomepage")
     */
    public function initAcceuilAction()
    {
        $em = $this->getDoctrine()->getManager();
        $conn = $this->get('database_connection');

        $statement = $conn->prepare('SELECT ID_PUB, e.idUsr,`username`, concat(u.prenom,\' \',u.nom) as nomprenom, u.photoprofile, `ETAT` , DATEPUB ,`NOM_EVENT` as titre, 
concat (DESCRIPTION,\' - \',`DATEDEBUT`, \' --> \', `DATEFIN` ,\' à \',`LIEU`) as DESCRIPTION, `AFFICHE`,\'evenement\' FROM `evenement` e, user u
WHERE e.idUsr = u.id
UNION
SELECT ID_PUB, e.ID_USR,`username`,concat(u.prenom,\' \',u.nom), u.photoprofile, `ETAT` , DATEPUB , 
concat(LIEUDEPART,\' - \',LIEUARRIVE) as titre,  DESCRIPTION , \'-\', \'covoiturage\' FROM `covoiturage` e, user u
WHERE e.ID_USR = u.id
UNION
SELECT ID_PUB, e.idUsr, `username`,concat(u.prenom,\' \',u.nom), u.photoprofile, "enattente" , DATEPUB ,
concat("Offre De Coloc : ","maison") as titre, concat(DESCRIPTION , \'\n\',\'Nombre chambres : \',NBCHAMBRE), IMGCOUVERTURE as \'affiche\',\'colocation\' FROM `colocation` e, user u
WHERE e.idUsr = u.id
UNION
SELECT idPub, e.idUsr, `username`,concat(u.prenom,\' \',u.nom), u.photoprofile,"Traitée",DATEPUB,\'Demande d aide\' as titre , concat( DESCRIPTION,\' - \',section, \' - \',matiere),DOCUMENT,\'aide\' FROM `aide` e, user u
WHERE e.idUsr = u.id
GROUP BY DATEPUB desc');

        $statement->execute();
        $publications = $statement->fetchAll();
        if ($publications != null) {
            $serializedEntity = $this->container->get('serializer')->serialize($publications, 'json');
            return new Response($serializedEntity);
        } else {
            return new Response("fail");
        }

    }


}
