<?php
/**
 * Created by PhpStorm.
 * User: Skander
 * Date: 02/04/2018
 * Time: 13:36
 */

namespace EducBundle\Service;


use Symfony\Component\Security\Core\Authentication\Token\Storage\TokenStorageInterface;

class UserService
{
    private $tokenStore;
    public function __construct(TokenStorageInterface $token)
    {
        $this->tokenStore = $token;
    }

    public function getUser(){
        $user = $this->tokenStore->getToken()->getUser();
        return $this->
    }

}