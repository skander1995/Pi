<?php

namespace EducBundle\Controller;

use EducBundle\Entity\Aide;
use EducBundle\Entity\Commentaire;
use EducBundle\Entity\Evaluaion;
use EducBundle\Form\AideType;



use Symfony\Bundle\FrameworkBundle\Controller\Controller;

use Symfony\Component\DependencyInjection\ContainerInterface;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;

use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;

use Doctrine\DBAL\DriverManager;
use Doctrine\DBAL\Connection;


class EducController extends Controller
{
    /**
     * @param \Symfony\Component\HttpFoundation\Request $request
     * @return \Symfony\Component\HttpFoundation\Response
     * @throws \Doctrine\DBAL\DBALException
     *
     */

    public function ratevalAction($idpub)
    {
        $em = $this->getDoctrine()->getManager();
        $rating = $em->getRepository('EducBundle:Evaluaion')->findBy(array('idPub'=>$idpub));
$div=0;
$somme=0;
$total=0;
        foreach ($rating as $item)
{
$div=$div+1;
$somme=$somme+$item->getNote();
    if ($div==0)
    {$total=0;}
    else{
        $total=$somme/$div;
    }
}
$send=array('nombre'=>$div,'note'=>$total);
       $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($send);
        return new JsonResponse($formatted);
    }

    public function ratemobAction($note,$iduser,$idpub)
    { $eva=new Evaluaion();
        $em = $this->getDoctrine()->getManager();
            $eva->setNote($note);
            $eva->setIdPub($idpub);
            $eva->setIdUsr($iduser);
            try{$em->persist($eva);
                $em->flush();
               }
            catch(\Exception $exception ){
                var_dump("error");
                $em = $this->getDoctrine()->resetManager();
                $eva=$em->getRepository("EducBundle:Evaluaion")->findOneBy(array('idPub'=>$idpub,'idUsr'=>$iduser));
                $eva->setNote($note);
                $em->persist($eva);
                $em->flush();

            }
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($eva);
        return new JsonResponse($formatted);

    }

    public function AddmobAction($idusr,$document,$description,$section,$matiere)
    {

        $em = $this->getDoctrine()->getManager();
        $us = $em->getRepository('AdminUserBundle:User')
            ->find($idusr);

        $aide = new Aide();
        $aide->setIdusr($us);
        $aide->setDocument($document);
        $aide->setDescription($description);
        $aide->setSection($section);
        $aide->setMatiere($matiere);
        $aide->setDatepub($this->getDate());

            $em = $this->getDoctrine()->getManager();
            $em->persist($aide);
            $em->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($aide);
        return new JsonResponse($formatted);


    }

    public function deletemobAction($id)
    {
        $em = $this->getDoctrine()->getManager();
        $aide = $em->getRepository("EducBundle:Aide")->find($id);
        $em->remove($aide);
        $em->flush();
        $serializer= new Serializer([new ObjectNormalizer()]);
        $formated= $serializer->normalize($aide) ;
        return new JsonResponse($formated);
    }
    public function AllmobAction()
    {
        $task=$this->getDoctrine()->getManager()
            ->getRepository('EducBundle:Aide')
            ->findAll();
        $serializer= new Serializer([new ObjectNormalizer()]);
        $formated= $serializer->normalize($task) ;
        return new JsonResponse($formated);
    }

    public function FindmobAction($id)
    {
        $task=$this->getDoctrine()->getManager()
            ->getRepository('EducBundle:Aide')
            ->find($id);
        $serializer= new Serializer([new ObjectNormalizer()]);
        $formated= $serializer->normalize($task) ;
        return new JsonResponse($formated);
    }




    public function All_commentsmobAction()
    {
        $task=$this->getDoctrine()->getManager()
            ->getRepository('EducBundle:Commentaire')
            ->findAll();
        $serializer= new Serializer([new ObjectNormalizer()]);
        $formated= $serializer->normalize($task) ;
        return new JsonResponse($formated);
    }
    public function Add_commentmobAction($idusr,$idpub,$contenu)
    {

        $em = $this->getDoctrine()->getManager();
        $us = $em->getRepository('UserBundle:User')
            ->find($idusr);
        $commentaire = new Commentaire();
        $commentaire->setIdUsr($us);
        $commentaire->setIdPub($idpub);
        $commentaire->setDateCom($this->getDate());
        $commentaire->setContenuCom($contenu);

        $em = $this->getDoctrine()->getManager();
        $em->persist($commentaire);
        $em->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($commentaire);
        return new JsonResponse($formatted);


    }

    public function delete_mobAction($id)
    {
        $em = $this->getDoctrine()->getManager();
        $aide = $em->getRepository("EducBundle:Aide")->find($id);
        $em->remove($aide);
        $em->flush();
        return $this->redirectToRoute('educ_affichage');
    }



//---------------------------------------------------------------------
    public function afficherAction(Request $request)
    {



        if ($this->container->get('security.authorization_checker')->isGranted('IS_AUTHENTICATED_FULLY')) {
            $user = $this->container->get('security.token_storage')->getToken()->getUser();
            $userId = $user->getId();
        }
        $id_currentuser = $userId;

        $em = $this->getDoctrine()->getManager();
        $current_user=$em->getRepository('AdminUserBundle\Entity\User')->find($id_currentuser);
        $conn = $this->get('database_connection');
        $aide = $em->getRepository("EducBundle:Aide")->findBy(array(), array('datepub' => 'ASC'));
        $commentaires = $em->getRepository('EducBundle:Commentaire')->findAll();
        $rating = $em->getRepository('EducBundle:Evaluaion')->findAll();


        $eva=new Evaluaion();
        $comm = new Commentaire();
        if ($request->isMethod('post')&& $request->get('formname')==2) {
            $eva->setNote($request->get('note'));
            $eva->setIdPub($request->get('idpub'));
            $eva->setIdUsr($id_currentuser);
            try{$em->persist($eva);
                $em->flush();
                return $this->redirectToRoute('educ_affichage');}
            catch(\Exception $exception ){
                var_dump("error");
                $em = $this->getDoctrine()->resetManager();
                $evan=$em->getRepository("EducBundle:Evaluaion")->findOneBy(array('idPub'=>$request->get('idpub'),'idUsr'=>$id_currentuser));
                $evan->setNote($request->get('note'));
                $em->persist($evan);
                $em->flush();
                return $this->redirectToRoute('educ_affichage');
            }
        }
        if ($request->isMethod('post')&& $request->get('formname')!=2){

            $comm->setContenuCom($request->get('cont'));
            $comm->setIdPub($request->get('ipbub'));
            $comm->setIdUsr($current_user);
            $comm->setDateCom($this->getDate());
            $em->persist($comm);
            $em->flush();
            return $this->redirectToRoute('educ_affichage');
        }



        $com_users = array();
        return $this->render('@Educ/Educ/affichage.html.twig',
            array('Aide' => $aide,
                  'comments' => $commentaires,
                  'com_users' => $com_users,
                  'current_user' => $current_user,
                  'rate' =>$rating

        ));
    }
    public function pubAction(Request $request,$id)
    {


        if ($this->container->get('security.authorization_checker')->isGranted('IS_AUTHENTICATED_FULLY')) {
            $user = $this->container->get('security.token_storage')->getToken()->getUser();
            $userId = $user->getId();
        }
        $id_currentuser = 2;

        $em = $this->getDoctrine()->getManager();
        $current_user=$em->getRepository('AdminUserBundle:User')->find($id_currentuser);
        $conn = $this->get('database_connection');
        $aide = $em->getRepository("EducBundle:Aide")->find($id);
        $commentaires = $em->getRepository('EducBundle:Commentaire')->findAll();
        $rating = $em->getRepository('EducBundle:Evaluaion')->findAll();


        $eva=new Evaluaion();
        $comm = new Commentaire();
        if ($request->isMethod('post')&& $request->get('formname')==2) {
            $eva->setNote($request->get('note'));
            $eva->setIdPub($request->get('idpub'));
            $eva->setIdUsr($id_currentuser);
            try{$em->persist($eva);
                $em->flush();
                return $this->redirectToRoute('educ_affichage');}
            catch(\Exception $exception ){
                var_dump("error");
                $em = $this->getDoctrine()->resetManager();
                $evan=$em->getRepository("EducBundle:Evaluaion")->findOneBy(array('idPub'=>$request->get('idpub'),'idUsr'=>$id_currentuser));
                $evan->setNote($request->get('note'));
                $em->persist($evan);
                $em->flush();


                return $this->redirectToRoute('educ_affichage');

            }
        }
        if ($request->isMethod('post')&& $request->get('formname')!=2){

            $comm->setContenuCom($request->get('cont'));
            $comm->setIdPub($request->get('ipbub'));
            $comm->setIdUsr($current_user);
            $comm->setDateCom($this->getDate());
            $em->persist($comm);
            $em->flush();


            $aidei = $em->getRepository("EducBundle:Aide")->find($request->get('ipbub'));

            $uzi = $em->getRepository("AdminUserBundle:User:User")->find($aidei->getIdusr());
            $messages = \Swift_Message::newInstance()
                ->setSubject('contacter')
                ->setFrom($current_user->getEmail())

                ->setTo($uzi->getEmail())
                ->setBody(
                    $this->renderView(
                        '@Educ/Educ/mail.html.twig'
                    ),
                    'text/html'
                );
            //$mailer=new  \Swift_Mailer('');
            $mailer =$this->get('mailer') ;
            $mailer->send($messages);


            return $this->redirectToRoute('educ_affichage');
        }



        $com_users = array();
        return $this->render('@Educ/Educ/publication.html.twig',
            array('Aide' => $aide,
                'comments' => $commentaires,
                'com_users' => $com_users,
                'current_user' => $current_user,
                'rate' =>$rating

            ));
    }

    public function affichageprivateAction(Request $request)
    {
        if ($this->container->get('security.authorization_checker')->isGranted('IS_AUTHENTICATED_FULLY')) {
            $user = $this->container->get('security.token_storage')->getToken()->getUser();
            $userId = $user->getId();
        }
        $id_currentuser = $userId;
        $em = $this->getDoctrine()->getManager();
        $current_user=$em->getRepository('AdminUserBundle:User')->find($id_currentuser);
        $conn = $this->get('database_connection');

        $comm = new Commentaire();
        if ($request->isMethod('post')) {
            $comm->setContenuCom($request->get('cont'));
            $comm->setIdPub($request->get('ipbub'));
            $comm->setIdUsr($current_user);
            $comm->setDateCom($this->getDate());
            $em->persist($comm);
            $em->flush();
            return $this->redirectToRoute('educ_affichage_private');
        }

        // var_dump($ume);
        $aide = $em->getRepository("EducBundle:Aide")->findBy(array('idusr'=>$id_currentuser), array('datepub' => 'ASC'));

        $commentaires = $em->getRepository('EducBundle:Commentaire')->findAll();


        $com_users = array();
        return $this->render('@Educ/Educ/mespublication.html.twig', array('Aide' => $aide
        , 'comments' => $commentaires, 'com_users' => $com_users, 'current_user' => $current_user
        ));
    }



    public  function pageaccueilAction()
    {
        if ($this->container->get('security.authorization_checker')->isGranted('IS_AUTHENTICATED_FULLY')) {
            $user = $this->container->get('security.token_storage')->getToken()->getUser();
            $userId = $user->getId();
        }
        $id_currentuser = 1;
        $em = $this->getDoctrine()->getManager();
        $current_user=$em->getRepository('AdminUserBundle:User')->find($id_currentuser);
        $users = $em->getRepository('AdminUserBundle:User')->findAll();
    // ...or getEntityManager() prior to Symfony 2.1
        $connection = $em->getConnection();
        $statement = $connection->prepare("SELECT `idPub` ,`datepub`,`description` ,`document` ,`section` ,`matiere` ,`idUsr` ,'aide'as type FROM aide");
        $statement->execute();
        $results = $statement->fetchAll();


        $statement2 = $connection->prepare("SELECT `ID_PUB`,`titre` ,`datepub`,`idUsr`,`DESCRIPTION` ,`LIEU` ,`LOYERMENSUEL` ,`NBCHAMBRE` ,`DATEDISPONIBILITE` ,'', 'colocation' as type
from Colocation");
        $statement2->execute();
        $results2 = $statement2->fetchAll();

        $statement3 = $connection->prepare("SELECT `ID_PUB` ,`datepub`,`ID_USR`,`LIEUDEPART` ,`LIEUARRIVE` ,`DATEDEPART` ,`DESCRIPTION` ,`NBPLACE` ,`PRIX`, 'covoiturage'as type 
from covoiturage");
        $statement3->execute();
        $results3 = $statement3->fetchAll();

        $statement4 = $connection->prepare("SELECT `ID_PUB` ,`datepub`,`idUsr`,`DESCRIPTION` ,`NOM_EVENT` ,`place_dispo` ,`LIEU` ,`DATEDEBUT` ,`DATEFIN` , 'evenement' as type
from evenements");
        $statement4->execute();
        $results4 = $statement4->fetchAll();
        $res=array_merge($results,$results2,$results3,$results4);

        return $this->render('@Educ/Educ/accueil.html.twig',
            array('pub'=>$res,
                'current_user'=>$current_user,
                'users'=>$users));
    }

    public function rechercheAideDqlAction(Request $request)
    {

        $em = $this->getDoctrine()->getManager();
        if ($request->isXmlHttpRequest()) {
            $serializer = new Serializer(array(new ObjectNormalizer()));
            $questions = $em->getRepository("EducBundle:Aide")->findEquipeDql($request->request->get("pays"));
            $data = $serializer->normalize($questions);
            return new JsonResponse($data);
        }


    }


    public function delete_comAction($id)
    {
        $em = $this->getDoctrine()->getManager();
        $aide = $em->getRepository("EducBundle:Commentaire")->find($id);
        $em->remove($aide);
        $em->flush();
        return $this->redirectToRoute('educ_affichage');
    }

    public function deleteAction($id)
    {
        $em = $this->getDoctrine()->getManager();
        $aide = $em->getRepository("EducBundle:Aide")->find($id);
        $em->remove($aide);
        $em->flush();
        return $this->redirectToRoute('educ_affichage');
    }


    public function updateAction(Request $request, $id)
    {
        $em = $this->getDoctrine()->getManager();
        $aide = $em->getRepository("EducBundle:Aide")->find($id);
        $aide->setDocument(null);
        $form = $this->createForm(AideType::class, $aide);
        $form->handleRequest($request);
        if ($form->isValid()) {
            /**
             * @var UploadedFile $file
             */
            $file = $aide->getDocument();
            $fileName = $this->generateUniqueFileName() . '.' . $file->guessExtension();
            $file->move($this->getParameter('upload_directory'), $fileName);
            $aide->setDocument($fileName);
            $em = $this->getDoctrine()->getManager();

            $em->flush();
            return $this->redirectToRoute('educ_affichage');
        }
        return $this->render('EducBundle:Educ:updateduc.html.twig', array(
            'form' => $form->createView()
        ));
    }


    public function AddAction(Request $request)
    {
        if ($this->container->get('security.authorization_checker')->isGranted('IS_AUTHENTICATED_FULLY')) {
            $user = $this->container->get('security.token_storage')->getToken()->getUser();
            $userId = $user->getId();
        }

        $id_currentuser = $userId;
        $em = $this->getDoctrine()->getManager();
        $us = $em->getRepository('AdminUserBundle:User')
            ->find($id_currentuser);

        $aide = new Aide();
        $aide->setIdusr($us);
        


        $form = $this->createForm(AideType::class, $aide);
        $form->handleRequest($request);
        if ($form->isValid()) {
            /**
             * @var UploadedFile $file
             */
            $file = $aide->getDocument();
            $fileName = $this->generateUniqueFileName() . '.' . $file->guessExtension();
            $file->move($this->getParameter('upload_directory'), $fileName);
            $aide->setDocument($fileName);
            $aide->setDatepub($this->getDate());

            $em = $this->getDoctrine()->getManager();
            $em->persist($aide);
            $em->flush();
            return $this->redirectToRoute('educ_affichage');
        }
        //return $this->render('EducBundle:Educ:ajout.html.twig');    }

        return $this->render('EducBundle:Educ:ajouteduc.html.twig', array(
            'form' => $form->createView()));
    }

    private function generateUniqueFileName()
    {
        return md5(uniqid());
    }

    /**
     *
     * @return \DateTime
     */
    public function getDate()
    {
        return new \DateTime('now', (new \DateTimeZone('Africa/Tunis')));
    }


}
