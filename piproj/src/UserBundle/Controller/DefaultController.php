<?php

namespace UserBundle\Controller;

use AdminUserBundle\Entity\User;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use FOS\UserBundle\Event\FilterUserResponseEvent;
use FOS\UserBundle\Event\FormEvent;
use FOS\UserBundle\Event\GetResponseUserEvent;
use FOS\UserBundle\Form\Factory\FactoryInterface;
use FOS\UserBundle\FOSUserEvents;
use FOS\UserBundle\Model\UserInterface;
use FOS\UserBundle\Model\UserManagerInterface;
use Symfony\Component\EventDispatcher\EventDispatcherInterface;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpKernel\Exception\NotFoundHttpException;
use Symfony\Component\Security\Core\Exception\AccessDeniedException;
use Tebru\Gson\Gson;

class DefaultController extends Controller
{
    public function indexAction()
    {
        return $this->render('UserBundle:Default:index.html.twig');
    }

    public function uploadPhotoAction($id, Request $request)
    {
        $allowedExts = array("gif", "jpeg", "jpg", "png");
        $temp = explode(".", $_FILES["file"]["name"]);
        $extension = end($temp);
        if ((($_FILES["file"]["type"] == "image/gif") || ($_FILES["file"]["type"] == "image/jpeg") || ($_FILES["file"]["type"] == "image/jpg") || ($_FILES["file"]["type"] == "image/pjpeg") || ($_FILES["file"]["type"] == "image/x-png") || ($_FILES["file"]["type"] == "image/png")) && ($_FILES["file"]["size"] < 5000000) && in_array($extension, $allowedExts)) {
            if ($_FILES["file"]["error"] > 0) {
                $named_array = array("Status" => "error");
                // echo json_encode($named_array);

                return new Response("error");

            } else {

                $uploaddir = realpath('./') . '/';
                $dir = "C:/xampp/htdocs";
                $serdir = "/espentreaide/uploadable/images/";
                $uploadfile = $dir . $serdir . basename($_FILES['file']['name']);
                move_uploaded_file($_FILES["file"]["tmp_name"], $uploadfile);


                $em = $this->getDoctrine()->getManager();
                $user = $em->getRepository('AdminUserBundle:User')->find($id);

                // persist newly uploaded image to the user
                $user->setPhotoProfile($serdir . basename($_FILES['file']['name']));
                $em->persist($user);

                /** @var $userManager \FOS\UserBundle\Model\UserManagerInterface */
                $userManager = $this->get('fos_user.user_manager');
                $userManager->updateUser($user);


                $Path = $_FILES["file"]["name"];
                $named_array = array("Status" => "ok");
                return new Response("ok");
            }
        } else {
            $named_array = array("Status" => "invalid");
            //echo json_encode($named_array);
            return new Response("invalid");

        }

    }

    public function registerAction($usrn, $pwd, $email, $nom, $prn, $dn)
    {
        /** @var $formFactory FactoryInterface */
        $formFactory = $this->get('fos_user.registration.form.factory');
        /** @var $userManager UserManagerInterface */
        $userManager = $this->get('fos_user.user_manager');

        $em = $this->getDoctrine()->getManager();

        $user = new User();


        $encoder_service = $this->get('security.encoder_factory');
        $encoder = $encoder_service->getEncoder($user);
        $encoded_pass = $encoder->encodePassword($pwd, $user->getSalt());

        $user->setEnabled(true);
        $user->setEmail($email);
        $user->setUsername($usrn);
        $user->setPlainPassword($pwd);
        $user->setPassword($encoded_pass);

        $user->setNom($nom);
        $user->setPrenom($prn);
        $user->setDatenaissance(new \DateTime($dn));


        $userManager->updateUser($user);

        $serializedEntity = $this->container->get('serializer')->serialize($user, 'json');
        if ($user != null) {
            return new Response($serializedEntity);
        } else {
            return new Response("fail");
        }
    }

    public function newUserFromJsonAction(Request $request)
    {
        /** @var $formFactory FactoryInterface */
        $formFactory = $this->get('fos_user.registration.form.factory');
        /** @var $userManager UserManagerInterface */
        $userManager = $this->get('fos_user.user_manager');

        $parametersAsArray = [];
        if ($content = $request->getContent()) {
            $parametersAsArray = json_decode($content, true);
        }

        // $em = $this->getDoctrine()->getManager();
        $user = new User();
        $jsonObject = $request->get('object');
        $data = json_decode($jsonObject);
        //$naissance = $data->dateinString;
        $naissance = $parametersAsArray['dateinString'];
        //$serializer = $this->container->get('serializer');
        //$user = $serializer->deserialize($jsonObject, User::class, 'json');

        $gson = Gson::builder()->build();
        $user = $gson->fromJson($jsonObject, User::class);

        $encoder_service = $this->get('security.encoder_factory');
        $encoder = $encoder_service->getEncoder($user);
        $encoded_pass = $encoder->encodePassword($user->getPassword(), $user->getSalt());

        $user->setDatenaissance(new \DateTime($naissance));
        $user->setEnabled(true);
        $user->setPlainPassword($user->getPassword());
        $user->setPassword($encoded_pass);
        $userManager->updateUser($user);

        $serializedEntity = $this->container->get('serializer')->serialize($user, 'json');
        if ($user != null) {
            return new Response($serializedEntity);
        } else {
            return new JsonResponse("fail");
        }
    }

    public function checkSocialIdAction($id)
    {
        $em = $this->getDoctrine()->getManager();
        $user = $em->getRepository('AdminUserBundle:User')->findOneBy(array(
            'socialid' => $id
        ));

        if ($user != null) {
            $serializedEntity = $this->container->get('serializer')->serialize($user, 'json');
            return new Response($serializedEntity);
        } else {
            return new Response("notfound");
        }
    }
}
